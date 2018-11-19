/**
 * 
 */
package com.towcent.base.notify;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.mapper.JsonMapper;
import com.towcent.base.common.model.NotifySmsTemplate;
import com.towcent.base.common.model.SmsDto;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.service.NotifySmsTemplateService;

/**
 * 发送短信组件(阿里云通信)
 * 
 * @author huangtao
 *
 */
@Service(value="aliyunSmsComponent")
public class AliyunSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(AliyunSmsComponent.class);
	
	// 产品名称:云通信短信API产品
	private static final String product = "Dysmsapi";
	// 产品域名
	private static final String domain = "dysmsapi.aliyuncs.com";
	
	@Value("${sms.ali.appkey}")
	private String appkey;
	@Value("${sms.ali.secret}")
	private String secret;
	
	@Resource
	SmsNotifyApi smsNotityApi;
	@Resource 
	RedisTemplateExt<String, Object> redisTemplateExt;
	@Resource
	private NotifySmsTemplateService templateService;
	
	@Override
	public SmsDto post(String mobile, int smsType, Object... params) throws RpcException {
		SmsDto resultVo = null;
		try {
			if (null == params) {
				params = new String[]{""};
			}
			NotifySmsTemplate template = getNotifySmsTemplateByType(smsType);
			// 1. 构建短信参数
			Map<String, Object> smsParams = this.bulidParamVo(template, params);

			// params[0] = smsParams.get("code");
			boolean sendFlag = false;    // 发送成功的标记
			if (!smsNotityApi.getSmsTestFlag()) {
				//可自助调整超时时间
		        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		        //初始化acsClient,暂不支持region化
		        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", appkey, secret);
		        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		        IAcsClient acsClient = new DefaultAcsClient(profile);

		        //组装请求对象-具体描述见控制台-文档部分内容
		        SendSmsRequest request = new SendSmsRequest();
		        //必填:待发送手机号
		        request.setPhoneNumbers(mobile);
		        //必填:短信签名-可在短信控制台中找到
		        request.setSignName(template.getSignature());
		        //必填:短信模板-可在短信控制台中找到
		        request.setTemplateCode(template.getSmsTemplateId());
		        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		        request.setTemplateParam(JsonMapper.toJsonString(smsParams));

		        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		        //request.setSmsUpExtendCode("90997");

		        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		        //request.setOutId("yourOutId");

		        //hint 此处可能会抛出异常，注意catch
		        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				
				// logger.debug(sendSmsResponse.getBody());
				// BizResult bizResult = rsp.getResult();
		        sendFlag = StringUtils.equals("OK", sendSmsResponse.getCode());
		        Integer code = sendFlag ? 0 : 1; 
		        
				resultVo = new SmsDto(code, sendSmsResponse.getMessage(), sendSmsResponse.getBizId());
			} else {
				resultVo = new SmsDto(0, "测试发送成功", "1220000001");
				sendFlag = true;
			}
			// 发送成功
			if (sendFlag && BaseConstant.YES.equals(template.getIsSecurityCode())) {
				String key = BaseCacheKey.getSmsKey((byte) smsType, mobile);
				redisTemplateExt.set(key, smsParams.get("code"), template.getValidTime(), TimeUnit.MINUTES);
				resultVo.setSmsContent(MessageFormat.format(template.getContent(), params));
			}else{
				resultVo.setSmsContent(MessageFormat.format(template.getContent(), params));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return resultVo;
	}
	
	@Override
	public SmsDto post(String areaCode, String mobile, int smsType, Object... params) throws RpcException {
		if (StringUtils.isBlank(areaCode) || "86".equals(areaCode)) {
			return post(mobile, smsType, params);
		}
		
		// 接收号码格式为00+国际区号+号码，如“0085200000000”
		return post("00" + areaCode + mobile, smsType + 100, params);
	}
	
	/**
	 * 构建参数
	 * @param template
	 * @param params
	 * @return
	 * @throws RpcException 
	 */
	private Map<String, Object> bulidParamVo(NotifySmsTemplate template, Object... params) throws RpcException {
//		if (null == params || "code".equals(template.getParamStr())) {
//			params = new String[]{""};
//		}
		
		Map<String, Object> maps = Maps.newHashMap();
		String[] paramQuerys = StringUtils.split(template.getParamStr(), ",");
		if (ArrayUtils.isEmpty(paramQuerys)) {
			return maps;
		}
		
		// 是否需要验证码
		String verifyCode = null;
		if (BaseConstant.YES.equals(template.getIsSecurityCode())) {
			verifyCode = smsNotityApi.generateVerifyCode();
		}
		
		for (int i = 0; i < paramQuerys.length; i++) {
			String str = paramQuerys[i];
			if ("code".equals(str)) {
				maps.put(str, verifyCode); // 验证码
				params[i] = verifyCode;
			} else {
				maps.put(str, params[i]);
			}
		}
		return maps;
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
