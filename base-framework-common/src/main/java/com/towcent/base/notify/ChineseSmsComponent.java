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
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.service.NotifySmsTemplateService;

/**
 * 中国网建-短信组件  http://www.smschinese.cn/api.shtml
 * @author huangtao
 *
 */
@Service(value="chineseSmsComponent")
public class ChineseSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(ChineseSmsComponent.class);
	
	// smschinese.sms.url=http://utf8.api.smschinese.cn/
	// smschinese.sms.uid=dofunctech
	// smschinese.sms.key=1a30af2083c8c536847a

	// 国内短信
	@Value("${smschinese.sms.url}")
	private String url;
	@Value("${smschinese.sms.uid}")
	private String uid;
	@Value("${smschinese.sms.key}")
	private String key;
	
	@Resource
	SmsNotifyApi smsNotityApi;
	@Resource 
	RedisTemplateExt<String, Object> redisTemplateExt;
	@Resource
	private NotifySmsTemplateService templateService;
	
	@Override
	public SmsDto post(String mobile, int smsType, Object... params) throws RpcException {
		SmsDto resultVo = null;
		
		Map<String, String> paramsMap = Maps.newHashMap();
		paramsMap.put("Uid", uid);
		paramsMap.put("Key", key);
		paramsMap.put("smsMob", mobile);
		// 短信模板
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
		paramsMap.put("smsText", smsContent);
        
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			String result = BaseHttpClient.postMethod(url, paramsMap);
			
			int code = Integer.valueOf(result) > 0 ? 0 : Integer.valueOf(result);
	        resultVo = new SmsDto(code, statusMap.get(code), "");
			if (code == 0) {
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
		
		// 短信模板
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType + 100);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
        
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			// TODO 未提供
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
	
	private static final Map<Integer, String> statusMap = Maps.newHashMap();
	
	static {
		statusMap.put(0, "提交成功");
		statusMap.put(-1, "没有该用户账户");
		statusMap.put(-2, "接口密钥不正确 [查看密钥]不是账户登陆密码");
		statusMap.put(-21, "MD5接口密钥加密不正确");
		statusMap.put(-3, "短信数量不足");
		statusMap.put(-11, "该用户被禁用");
		statusMap.put(-14, "短信内容出现非法字符");
		statusMap.put(-4, "手机号格式不正确");
		statusMap.put(-41, "手机号码为空");
		statusMap.put(-42, "短信内容为空");
		statusMap.put(-51, "短信签名格式不正确接口签名格式为：【签名内容】");
		statusMap.put(-52, "短信签名太长建议签名10个字符以内");
		statusMap.put(-6, "IP限制");
	}
}
