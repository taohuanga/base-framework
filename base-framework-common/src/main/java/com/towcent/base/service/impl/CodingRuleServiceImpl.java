package com.towcent.base.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.towcent.base.common.config.SpringConfig;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.enums.RuleTypeEnum;
import com.towcent.base.common.exception.DaoException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.CodingRule;
import com.towcent.base.common.model.CodingRuleBuilder;
import com.towcent.base.common.model.CodingRuleBuilderExt;
import com.towcent.base.common.model.CodingRuleHolder;
import com.towcent.base.common.model.DefaultCodingRuleBuilder;
import com.towcent.base.common.utils.CodingRuleUtil;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.dal.db.CodingRuleMapper;
import com.towcent.base.service.CodingRuleService;

/**
 * 编码规则实现类 方法实际上都已经实现，定义为 abstract只是提醒其他人去继承
 * 
 */
@Service
public class CodingRuleServiceImpl implements CodingRuleService {
	private Map<String, CodingRuleHolder> cacheMap = new ConcurrentHashMap<String, CodingRuleHolder>();
	private static ConcurrentMap<String, String> lockMap = new ConcurrentHashMap<String, String>();

	@Resource
	private CodingRuleMapper mapper;
	
	@Override
	public String getSerialNo(RuleTypeEnum type, Integer merchantId) throws ServiceException {
		return getSerialNo(type.getName(), merchantId);
	}
	
	/*
	 * 获取编号 使用自己单独的事务REQUIRES_NEW，防止被加入外部事务，被外部事务的锁或响应时间影响本接口的性能
	 * 
	 * @return String
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public String getSerialNo(String requestId, Integer merchantId) throws ServiceException {
		CodingRuleBuilder builder = new DefaultCodingRuleBuilder();
		builder.setRequestId(requestId);
		builder.setMerchantId(merchantId);   // 商户Id
		return getSerialNo(builder);
	}

	/**
	 * 回收序列号
	 * 
	 * @param requestId
	 * @param serialNo
	 */
	public void recycleSerialNo(String requestId, String serialNo, Integer merchantId) {
		if (requestId == null) {
			return;
		}
		String newRequestId = requestId;
		if (null != merchantId) {
			newRequestId =  newRequestId + "#" + merchantId;
		}
		lockMap.putIfAbsent(newRequestId, newRequestId);
		synchronized (lockMap.get(newRequestId)) {
			if (cacheMap.get(newRequestId) != null) {
				cacheMap.get(newRequestId).recycleSerialNo(serialNo);
			}
		}
	}

	/**
	 * 从数据库中取序列号重新初始化map
	 * 含义理解：以每天重置模式为例，每天都必须将reset_time设置为新的一天，且同时将current_serial_no重置为1，
	 * 然后current_serial_no不断递增，直到第二天再重置； 今天的某个时间点服务器重启了，也可以通过reset_time知道今天是否重置过
	 * 
	 * @param requestId
	 * @return
	 * @throws DaoException
	 */
	private CodingRule initMap(String requestId, Integer merchantId) throws DaoException {
		String newRequestId = requestId;
		if (null != merchantId) {
			newRequestId =  newRequestId + "#" + merchantId;
		}
		
		CodingRule codingRule = null;
		SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
		codingRule = mapper.selectByRequestId(requestId, merchantId, config.getJdbcType());
		// 获取到current_serial_no时的本地时间，用来计算是否需要重置日期用的
		long currentLocalTime = System.currentTimeMillis();
		if (codingRule == null) {
			return null;
		}

		int step = 1;// 增长步长
		if (codingRule.getAllowBreakNo()) {
			step = BaseConstant.CACHED_SIZE;
		}

		// 如果需要重置时间
		if (needResetTime(codingRule)) {
			// 重置current_serial_no=1，reset_time=now()
			mapper.resetCurrentSerialNo(codingRule.getRequestId(), step, merchantId, config.getJdbcType());
			// 此处不去数据库中重新查询了，直接根据旧的codingRule计算
			codingRule.setCurrentSerialNo(1);
			// codingRule.set TODO 可能要设置其他字段，以便在codingRuleHolder中统一处理，否则
		} else {
			mapper.increaseSerialNo(requestId, step, merchantId);
		}

		// 编码规则为非抽象（即具体的）才加入缓存
		if (!codingRule.getIsAbstract()) {
			cacheMap.put(newRequestId, new CodingRuleHolder(codingRule, currentLocalTime));
		}

		return codingRule;
	}

	/**
	 * 判断是否需要重置reset_time和current_serial_no，主要是比较resetTime和dbTime的值
	 * 
	 * @param codingRule
	 * @return
	 */
	private boolean needResetTime(CodingRule codingRule) {
		int resetMode = codingRule.getResetMode();
		// resetMode==0表示永不重置
		if (resetMode == 0) {
			return false;
		}
		String resetTime = CodingRuleUtil.getFormatedDate(resetMode, codingRule.getResetTime());

		// dbTime为数据库时间，不可能为空
		String dbTime = CodingRuleUtil.getFormatedDate(resetMode, codingRule.getDbTime());

		// resetTime为空就必须reset,或者resetTime不等于dbTime也需要reset
		if ("".equals(resetTime) || !dbTime.equals(resetTime)) {
			return true;
		}

		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public String getSerialNo(CodingRuleBuilder builder) throws ServiceException {
		if (builder == null) {
			throw new ServiceException("CodingRuleBuilder is null :");
		}

		return this.getSerialNoInner(builder, null);
	}

	private String getSerialNoInner(CodingRuleBuilder builder, String subRequestId) throws ServiceException {
		if (builder == null) {
			throw new ServiceException("CodingRuleBuilder is null :");
		}
		String requestId = builder.getRequestId();
		if (StringUtils.isNotBlank(subRequestId)) {
			requestId = requestId + "#" + subRequestId;
		}
		
		if (null != builder.getMerchantId()) {
			requestId =  requestId + "#" + builder.getMerchantId();
		}
		
		String serial = "";
		try {
			lockMap.putIfAbsent(requestId, requestId);
			synchronized (lockMap.get(requestId)) {
				if (cacheMap.get(requestId) == null) {
					CodingRule codingRule = this.initMap(builder.getRequestId(), builder.getMerchantId());
					// 没有找到该编码规则,返回空串
					if (codingRule == null) {
						if (requestId.indexOf("#") > 0) {
							String[] arr = requestId.split("#");
							String baseRequestId = arr[0];

							// 将baseRequestId加入lockMap
							lockMap.putIfAbsent(baseRequestId, baseRequestId);
							synchronized (lockMap.get(baseRequestId)) {
								SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
								codingRule = mapper.selectByRequestId(baseRequestId, builder.getMerchantId(), config.getJdbcType());
								if (codingRule == null) {
									// 数据库中没有的requestId移除，防止lockMap中被无用的key占用
									lockMap.remove(baseRequestId);
									lockMap.remove(requestId);
									throw new ServiceException("no coding rule for :" + requestId);
								} else {
									// 从base记录复制一条记录（比如每个门店复制一条编码规则）
									copyCodingRule(builder.getRequestId(), builder.getMerchantId(), codingRule);
								}
							}
							codingRule = this.initMap(builder.getRequestId(), builder.getMerchantId());
						} else {
							throw new ServiceException("no coding rule for :" + requestId);
						}
					} else {
						if (codingRule.getIsAbstract()) {
							lockMap.remove(requestId);
							throw new ServiceException("coding rule is Abstract,requestId is:" + requestId);
						}
					}
				}

				serial = cacheMap.get(requestId).nextSerialNo();
				if (serial.equals(BaseConstant.NO_CACHED_NO) // 缓存的序列号用完了，重新从数据库取
						|| serial.equals(BaseConstant.NEED_RESET)) {// 重置currentSerialNo，（日期变了重置、超过最大值重置）
					CodingRule codingRule = initMap(builder.getRequestId(), builder.getMerchantId());
					if (codingRule.getIsAbstract()) {
						codingRule = null;
						throw new ServiceException("coding rule is Abstract,requestId is:" + requestId);
					}
					serial = cacheMap.get(requestId).nextSerialNo();
				}
			}
			// 如果是使用回收的编码，直接返回（回收的是已经构造好的）
			if (cacheMap.get(requestId).getIsUseRecycle().compareAndSet(true, false)) {
				return serial;
			} else {// 否则需要调用buildNo()构造
				builder.setPrefix(cacheMap.get(requestId).getPrefix());
				builder.setFormatedTime(cacheMap.get(requestId).getFormatedDate());
				builder.setSequence(serial);
				return builder.buildNo();
			}

		} catch (DaoException e) {
			throw new ServiceException("getSerialNo DaoException", e);
		}
	}

	private void copyCodingRule(String requestId, Integer merchantId, CodingRule codingRule) {
		codingRule.setRequestId(requestId);
		codingRule.setId(null);
		codingRule.setIsAbstract(false);
		codingRule.setCurrentSerialNo(1);
		codingRule.setMerchantId(merchantId);
		mapper.insert(codingRule);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public String getSerialNo(CodingRuleBuilderExt builderExt) throws ServiceException {
		String requestId = builderExt.getRequestId();
		String subRequestId = builderExt.getSubRequestId();
		if (requestId == null || requestId.equals("")) {
			throw new ServiceException("requestId can't be null or blank");
		}
		if (subRequestId == null || subRequestId.equals("")) {
			throw new ServiceException("subRequestId can't be null or blank");
		}
		return getSerialNoInner(builderExt, subRequestId);
	}

}