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

import com.google.common.collect.Maps;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
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
 * 发送短信组件(阿里大于)
 * 
 * @author huangtao
 *
 */
@Service(value="aliSmsComponent")
public class AliSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(AliSmsComponent.class);
	
	@Value("${sms.ali.url}")
	private String url;
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
			NotifySmsTemplate template = getNotifySmsTemplateByType(smsType);
			// 1. 构建短信参数
			Map<String, Object> smsParams = this.bulidParamVo(template, params);
			
			boolean sendFlag = false;    // 发送成功的标记
			if (!smsNotityApi.getSmsTestFlag()) {
				TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
				AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
				req.setExtend("");
				req.setSmsType("normal");
				req.setSmsFreeSignName(template.getSignature());
				req.setSmsParamString(JsonMapper.toJsonString(smsParams));
				req.setRecNum(mobile);
				req.setSmsTemplateCode(template.getSmsTemplateId());
				AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
				// logger.debug(rsp.getBody());
				BizResult bizResult = rsp.getResult();
				resultVo = new SmsDto(Integer.valueOf(bizResult.getErrCode()), bizResult.getMsg(), bizResult.getModel());
				sendFlag = bizResult.getSuccess();
			} else {
				resultVo = new SmsDto(0, "测试发送成功", "1220000001");
				sendFlag = true;
			}
			// 发送成功
			if (sendFlag && BaseConstant.YES.equals(template.getIsSecurityCode())) {
				String key = BaseCacheKey.getSmsKey((byte) smsType, mobile);
				redisTemplateExt.set(key, smsParams.get("code"), template.getValidTime(), TimeUnit.MINUTES);
			}
			resultVo.setSmsContent(MessageFormat.format(template.getContent(), params));
		} catch (ApiException e) {
			logger.error("", e);
		}
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
	private Map<String, Object> bulidParamVo(NotifySmsTemplate template, Object... params) throws RpcException {
		if (null == params || "code".equals(template.getParamStr())) {
			params = new String[]{""};
		}
		
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
