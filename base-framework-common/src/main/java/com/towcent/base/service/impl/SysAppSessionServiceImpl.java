package com.towcent.base.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.SysAppSession;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysAppSessionMapper;
import com.towcent.base.service.SysAppSessionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Generator
 * @date 2015-11-19 15:54:07
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysAppSessionService")
public class SysAppSessionServiceImpl extends BaseCrudServiceImpl implements SysAppSessionService {

	@Resource
	private SysAppSessionMapper sysAppSessionMapper;

	@Override
	public CrudMapper init() {
		return sysAppSessionMapper;
	}

	@Override
	public SysAppSession getAppSessionByParam(Integer merchantId, String account, String deviceNo, Integer appType) throws ServiceException {
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("account", account);
			params.put("deviceNo", deviceNo);
			params.put("appType", appType);
			params.put("delFlag", BaseConstant.DEL_FLAG_0);
			params.put("failureTime", new Date());
			params.put("merchantId", merchantId);
			List<SysAppSession> list = sysAppSessionMapper.selectByParams(params);
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<String> getAppTokenListByParam(Integer merchantId, String account, Byte appType) throws ServiceException {
		List<String> tokens = Lists.newArrayList();
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("account", account);
			params.put("appType", appType);
			params.put("sysType", BaseConstant.SYS_TYPE_1);
			params.put("delFlag", BaseConstant.DEL_FLAG_0);
			params.put("failureTime", new Date());
			params.put("merchantId", merchantId);
			List<SysAppSession> list = sysAppSessionMapper.selectByParams(params);
			if (!CollectionUtils.isEmpty(list)) {
				for (SysAppSession session : list) {
					if (StringUtils.isNotBlank(session.getDeviceToken())) {
						tokens.add(session.getDeviceToken());
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException("", e);
		}

		return tokens;
	}
	
	@Override
	public List<String> getAppTokenIdsListByParam(Integer merchantId, String account, Byte appType) throws ServiceException {
		List<String> tokens = Lists.newArrayList();
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("account", account);
			params.put("appType", appType);
			params.put("delFlag", BaseConstant.DEL_FLAG_0);
			params.put("failureTime", new Date());
			params.put("merchantId", merchantId);
			List<SysAppSession> list = sysAppSessionMapper.selectByParams(params);
			if (!CollectionUtils.isEmpty(list)) {
				for (SysAppSession session : list) {
					tokens.add(session.getTokenId());
				}
			}
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
		return tokens;
	}

	@Override
	public void deleteByAccount(Integer merchantId, String account, Byte appType) {
		sysAppSessionMapper.deleteByAccount(account, appType, merchantId);
	}
}