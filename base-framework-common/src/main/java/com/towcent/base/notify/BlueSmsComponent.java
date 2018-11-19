package com.towcent.base.notify;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.NotifySmsTemplate;
import com.towcent.base.common.model.SmsDto;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.notify.chuanglan.request.SmsI18nRequest;
import com.towcent.base.notify.chuanglan.request.SmsSendRequest;
import com.towcent.base.notify.chuanglan.response.SmsSendResponse;
import com.towcent.base.notify.chuanglan.util.ChuangLanSmsUtil;
import com.towcent.base.service.NotifySmsTemplateService;

/**
 * 创蓝-短信组件  https://www.253.com/index
 * @author huangtao
 *
 */
@Service(value="blueSmsComponent")
public class BlueSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(BlueSmsComponent.class);
	
	// blue.sms.url=http://smssh1.253.com/msg/send/json
	// blue.sms.account=N5020214
	// blue.sms.password=U8eldE5A0j5c8a
	// blue.sms.int.url=http://intapi.253.com/send/json
	// blue.sms.int.account=I3572322
	// blue.sms.int.password=BxpejCiHoPb7d5
	
	// 国内短信
	@Value("${blue.sms.url}")
	private String url;
	@Value("${blue.sms.account}")
	private String account;
	@Value("${blue.sms.password}")
	private String password;
	
	// 国际短信
	@Value("${blue.sms.int.url}")
	private String intUrl;
	@Value("${blue.sms.int.account}")
	private String intAccount;
	@Value("${blue.sms.int.password}")
	private String intPassword;
	
	@Resource
	SmsNotifyApi smsNotityApi;
	@Resource 
	RedisTemplateExt<String, Object> redisTemplateExt;
	@Resource
	private NotifySmsTemplateService templateService;
	
	@Override
	public SmsDto post(String mobile, int smsType, Object... params) throws RpcException {
		SmsDto resultVo = null;
		
		// 短信模板
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
		
		// 是否需要短信发送报告
		String report = "true";
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password,
        		smsContent, mobile, report);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			String response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);
	        SmsSendResponse resp = (SmsSendResponse) JSON.parseObject(response,
	                SmsSendResponse.class);
	        resultVo = new SmsDto(Integer.valueOf(resp.getCode()), MapUtils.getString(statusMap, resp.getCode(), ""), resp.getMsgId());
			if ("0".equals(resp.getCode())) {
				sendFlag = true;				
			}
		} else {
			resultVo = new SmsDto(0, "测试发送成功", "1220000001");
			sendFlag = true;
		}
		
		// 发送成功
		if (sendFlag && BaseConstant.YES.equals(template.getIsSecurityCode())) {
			String key = BaseCacheKey.getSmsKey((byte) smsType, mobile);
			redisTemplateExt.set(key, bParams[0], template.getValidTime(), TimeUnit.MINUTES);
		}
		resultVo.setSmsContent(smsContent);
		return resultVo;
	}
	
	@Override
	public SmsDto post(String areaCode, String mobile, int smsType, Object... params) throws RpcException {
		// 如果是国内手机号码，则使用普通短信接口
		if (StringUtils.isBlank(areaCode) || "86".equals(areaCode)) {
			return post(mobile, smsType, params);
		}
		
		SmsDto resultVo = null;
		
		// 短信模板  -- 国际短信模板id再原有模板加100
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType + 100);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
		
		SmsI18nRequest smsSingleRequest = new SmsI18nRequest(intAccount, intPassword,
        		smsContent, areaCode + mobile);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			String response = ChuangLanSmsUtil.sendSmsByPost(intUrl, requestJson);
	        SmsSendResponse resp = (SmsSendResponse) JSON.parseObject(response,
	                SmsSendResponse.class);
	        resultVo = new SmsDto(Integer.valueOf(resp.getCode()), MapUtils.getString(intStatusMap, resp.getCode(), ""), resp.getMsgId());
			if ("0".equals(resp.getCode())) {
				sendFlag = true;				
			}
		} else {
			resultVo = new SmsDto(0, "测试发送成功", "1220000001");
			sendFlag = true;
		}
		
		// 发送成功
		if (sendFlag && BaseConstant.YES.equals(template.getIsSecurityCode())) {
			String key = BaseCacheKey.getSmsKey((byte) smsType, mobile);
			redisTemplateExt.set(key, bParams[0], template.getValidTime(), TimeUnit.MINUTES);
		}
		resultVo.setSmsContent(smsContent);
		return resultVo;
	}
	
	/**
	 * 构建参数
	 * @param template
	 * @param params
	 * @return
	 * @throws RpcException 
	 */
	private String[] bulidParamVo(NotifySmsTemplate template, Object... params) throws RpcException {
		if (null == params || "code".equals(template.getParamStr())) {
			params = new String[]{""};
		}
		
		// 是否需要验证码
		String verifyCode = null;
		if (BaseConstant.YES.equals(template.getIsSecurityCode())) {
			verifyCode = smsNotityApi.generateVerifyCode();
			params[0] = verifyCode;
			return (String[]) params;
		}
		
		return (String[]) params;
	}
	
	private NotifySmsTemplate getNotifySmsTemplateByType(int smsType) {
		NotifySmsTemplate template = null;
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("smsType", smsType);
			List<NotifySmsTemplate> list = templateService.findByBiz(params);
			template = CollectionUtils.isEmpty(list) ? null : list.get(0);
		} catch (ServiceException e) {
			logger.error("", e);
		}
		return template;
	}
	
	private static final Map<String, String> statusMap = Maps.newHashMap();
	private static final Map<String, String> intStatusMap = Maps.newHashMap();
	
	static {
		statusMap.put("0", "提交成功");
		statusMap.put("101", "无此用户");
		statusMap.put("102", "密码错");
		statusMap.put("103", "提交过快（提交速度超过流速限制）");
		statusMap.put("104", "系统忙（因平台侧原因，暂时无法处理提交的短信）");
		statusMap.put("105", "敏感短信（短信内容包含敏感词）");
		statusMap.put("106", "消息长度错（>536或<=0）");
		statusMap.put("107", "包含错误的手机号码");
		statusMap.put("108", "手机号码个数错（群发>50000或<=0）");
		statusMap.put("109", "无发送额度（该用户可用短信数已使用完）");
		statusMap.put("110", "不在发送时间内");
		statusMap.put("113", "扩展码格式错（非数字或者长度不对）");
		statusMap.put("114", "可用参数组个数错误（小于最小设定值或者大于1000）;变量参数组大于20个");
		statusMap.put("116", "签名不合法或未带签名（在更换自己的签名需要在平台上报备后方可使用该签名）");
		statusMap.put("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
		statusMap.put("118", "用户没有相应的发送权限（账号被禁止发送）");
		statusMap.put("119", "用户已过期");
		statusMap.put("120", "违反防盗用策略(日发送限制)");
		statusMap.put("123", "发送类型错误");
		statusMap.put("124", "白模板匹配错误");
		statusMap.put("125", "匹配驳回模板，提交失败");
		statusMap.put("127", "定时发送时间格式错误");
		statusMap.put("128", "内容编码失败");
		statusMap.put("129", "JSON格式错误");
		statusMap.put("130", "请求参数错误（缺少必填参数）");

		intStatusMap.put("0", "提交成功");
		intStatusMap.put("101", "账号不存在");
		intStatusMap.put("102", "密码错误");
		intStatusMap.put("106", "短信内容长度错误(>536)");
		intStatusMap.put("108", "手机号码格式错误(>20或<5)");
		intStatusMap.put("110", "余额不足");
		intStatusMap.put("112", "产品配置错误");
		intStatusMap.put("114", "请求ip和绑定ip不一致");
		intStatusMap.put("115", "没有开通国内短信权限");
		intStatusMap.put("123", "短信内容不能为空");
		intStatusMap.put("128", "账号长度错误(>50或<=0)");
		intStatusMap.put("129", "产品价格配置错误");

	}
}
