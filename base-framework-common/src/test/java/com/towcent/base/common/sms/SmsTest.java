package com.towcent.base.common.sms;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.towcent.base.BaseTest;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.SmsParamDto;
import com.towcent.base.common.utils.BaseHttpClient;
import com.towcent.base.common.utils.DateUtils;
import com.towcent.base.manager.SmsNotifyApi;

public class SmsTest extends BaseTest {
	
	@Resource SmsNotifyApi notifyApi;
	
	@Test
	public void sendSms() throws RpcException {
		SmsParamDto param = new SmsParamDto();
		param.setMobile("18666296035");
		param.setSmsType((byte) 1);
		// param.setSmsParams(smsParams);
		notifyApi.sendSms(param);
	}
	
	/**
	 * 闪信通
	 * @throws RpcException
	 */
	@Test
	public void sendSms2() throws RpcException {
		Map<String, String> httpParams = Maps.newHashMap();
		httpParams.put("UserName", "900627");
		httpParams.put("Password", "dzsw2014");
//		httpParams.put("TimeStamp", "");
		httpParams.put("MobileNumber", "18666296035,");
		httpParams.put("MsgContent", "您的验证码为:617621  非本人操作请忽略。【万家耕社客服中心】");  // 短信内容
		
		String result = BaseHttpClient.postMethod("http://119.145.253.67:8080/edeeserver/sendSMS.do", httpParams);
		System.out.println(result);
	}
	
	@Test
	public void getSmsStatus() throws RpcException {
		Map<String, String> httpParams = Maps.newHashMap();
		httpParams.put("UserName", "900627");
		httpParams.put("Password", "dzsw2014");
		httpParams.put("StartTime", "201708122201");
		httpParams.put("EndTime", DateUtils.formatDate(new Date(), "yyyyMMddHHmm"));
		httpParams.put("PageSize", "100");
		httpParams.put("PageNo", "1");  // 短信内容
		
		String result = BaseHttpClient.postMethod("http://119.145.253.67:8080/edeeserver/status.do", httpParams);
		System.out.println(result);
	}
	
	@Test
	public void getSmsAccount() throws RpcException {
		Map<String, String> httpParams = Maps.newHashMap();
		httpParams.put("UserName", "900627");
		httpParams.put("Password", "dzsw2014");
		httpParams.put("TimeStamp", DateUtils.formatDate(new Date(), "yyyyMMddHHmm"));
		
		String result = BaseHttpClient.postMethod("http://119.145.253.67:8080/edeeserver/account.do", httpParams);
		System.out.println(result);
	}
}
