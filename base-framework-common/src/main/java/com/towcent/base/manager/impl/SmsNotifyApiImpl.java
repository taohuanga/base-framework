/**
 * 
 */
package com.towcent.base.manager.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.NotifySms;
import com.towcent.base.common.model.SmsDto;
import com.towcent.base.common.model.SmsParamDto;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.Assert;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.common.utils.SMSUtils;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.notify.SmsComponent;
import com.towcent.base.service.BaseService;
import com.towcent.base.service.NotifySmsService;

/**
 * 通知接口(阿里大于)
 * 
 * @author huangtao
 *
 */
@Service
public class SmsNotifyApiImpl extends BaseService implements SmsNotifyApi {
	
	@Resource NotifySmsService smsService;
	@Resource RedisTemplateExt<String, Object> redisTemplateExt;
	
	@Value("${sms.test.flag}")
	private boolean testFlag = false;  // 测试开关
	@Value("${sms.test.code}")
	private String testCode = "9999";  // 测试短信验证码 
	@Value("${sms.code.length}")
	private int smsLength = 4;         // 短信验证码长度
	@Value("${sms.project}")
	private String smsProject = "ali"; // 短信方案(默认:ali 闪信通:edee 阿里大于:ali 阿里云:aliyun 创蓝:blue)
	
	@Override
	public boolean getSmsTestFlag() {
		return testFlag;
	}
	
	@Override
	public String generateVerifyCode() throws RpcException {
		if (testFlag) {
			return testCode;
		} else {
			return SMSUtils.createRandom(smsLength);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.towcent.platform.app.client.notify.service.NotifyApi#sendSms(com.towcent.platform.app.client.notify.dto.SmsParamDto)
	 */
	@Override
	public boolean sendSms(SmsParamDto param) throws RpcException {
		validationObj(param);
		
		SmsComponent smsComponent = SpringContextHolder.getBean(smsProject + "SmsComponent");
		try {
			SmsDto dto = smsComponent.post(param.getAreaCode(), param.getMobile(), param.getSmsType(), param.getSmsParams());
			NotifySms vo = new NotifySms();
			vo.setContent(dto.getSmsContent());
			vo.setCreateDate(new Date());
			if (StringUtils.isNotBlank(param.getAreaCode())) {
				vo.setPhone("+" + param.getAreaCode() + "|" + param.getMobile());
			} else {
				vo.setPhone(param.getMobile());
			}
			vo.setSmsType(param.getSmsType());
			vo.setRemarks(dto.getMsg());
			vo.setSmsid(dto.getSmsid());
			vo.setSmsStatus((byte) (dto.getCode() == 0 ? 2 : 0));
			
			smsService.add(vo);
			
			boolean result = (0 == dto.getCode());
			
			logger.debug("发送短信:{}, 发送状态:{}", dto.getSmsContent(), result);
			return result;
		} catch (Exception e) {
			logger.error("", e);
			throw new RpcException("", e);
		}
	}
	
	@Override
	public boolean verifySmsCode(SmsParamDto param) throws RpcException {
		validationObj(param);
		Assert.isNotEmpty(param.getSecurityCode(), "验证码不能为空");
		
		try {
			String key = BaseCacheKey.getSmsKey(param.getSmsType(), param.getMobile());
			String code = (String) redisTemplateExt.get(key);
			boolean result = StringUtils.equals(param.getSecurityCode(), code);
			if (result) {
				redisTemplateExt.del(key);
			}
			return result;
		} catch (Exception e) {
			logger.error("", e);
			throw new RpcException("", e);
		}
	}
	
	@Override
	public void updateSmsReport(String msgId, boolean result, String msg) throws RpcException {
		Assert.isNotEmpty(msgId, "消息不能为空");
		
		try {
			NotifySms notifySms = smsService.findByKeyValSingle("smsid", msgId);
			notifySms.setSmsStatus((byte) (result ? 3 : 1));
			notifySms.setRemarks(msg);
			notifySms.setUpdateDate(new Date());
			
			smsService.modifyById(notifySms);
		} catch (ServiceException e) {
			logger.error("", e);
			throw new RpcException("", e);
		}
	}

}
