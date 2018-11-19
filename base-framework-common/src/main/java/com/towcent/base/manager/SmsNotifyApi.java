package com.towcent.base.manager;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.SmsParamDto;

/**
 * 短信接口
 * @author huangtao
 *
 */
public interface SmsNotifyApi {
	
	/**
	 * 返回短信测试开关(true:测试   false:)
	 * @return
	 */
	boolean getSmsTestFlag();
	
	/**
	 * 生成验证码
	 * @return
	 * @throws RpcException
	 */
	String generateVerifyCode() throws RpcException;
	
	/**
	 * 发送短信
	 * @param param
	 * @return
	 * @throws RpcException
	 */
	boolean sendSms(SmsParamDto param) throws RpcException;
	
	/**
	 * 校验短信验证码
	 * @param param 
	 * @return 
	 * @throws RpcException
	 */
	boolean verifySmsCode(SmsParamDto param) throws RpcException;
	
	/**
	 * 更改短信发送状态.
	 * @param msgId    消息Id
	 * @param result   结果
	 * @param msg      备注
	 * @throws RpcException
	 */
	void updateSmsReport(String msgId, boolean result, String msg) throws RpcException;
}
