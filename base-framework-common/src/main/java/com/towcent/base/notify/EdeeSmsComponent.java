package com.towcent.base.notify;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.NotifySmsTemplate;
import com.towcent.base.common.model.SmsDto;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.common.utils.BaseHttpClient;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.service.NotifySmsTemplateService;

/**
 * 闪信通-短信组件
 * @author huangtao
 *
 */
@Service(value="edeeSmsComponent")
public class EdeeSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(EdeeSmsComponent.class);
	
	// sxt.sms.service.url=http://119.145.253.67:8080/edeeserver/sendSMS.do
	// sxt.sms.service.username=900627
	// sxt.sms.service.password=dzsw2014
	// sxt.sms.content.sign=【万家耕社客服中心】
	
	@Value("${sxt.sms.service.url}")
	private String url;
	@Value("${sxt.sms.service.username}")
	private String username;
	@Value("${sxt.sms.service.password}")
	private String password;
	@Value("${sxt.sms.content.sign}")
	private String sign;
	
	@Resource
	SmsNotifyApi smsNotityApi;
	@Resource 
	RedisTemplateExt<String, Object> redisTemplateExt;
	@Resource
	private NotifySmsTemplateService templateService;
	
	@Override
	public SmsDto post(String mobile, int smsType, Object... params) throws RpcException {
		SmsDto resultVo = null;
		
		Map<String, String> httpParams = Maps.newHashMap();
		httpParams.put("UserName", username);
		httpParams.put("Password", password);
		httpParams.put("MobileNumber", mobile);
		
		// 短信模板
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = MessageFormat.format(template.getContent(), bParams) + sign;
		httpParams.put("MsgContent", smsContent);  // 短信内容
		
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			String result = BaseHttpClient.postMethod(url, httpParams);
			if ("0".equals(result)) {
				resultVo = new SmsDto(0, "提交成功", "");
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
		return post(mobile, smsType, params);
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
}
