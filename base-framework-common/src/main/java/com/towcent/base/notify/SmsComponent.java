/**
 * 
 */
package com.towcent.base.notify;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.SmsDto;

/**
 * 发送短信组件
 * 
 * @author huangtao
 *
 */
public interface SmsComponent {
	
	/**
	 * 发送短信
	 * @param mobile
	 * @param smsType
	 * @param params
	 * @return
	 * @throws RpcException
	 */
	SmsDto post(String mobile, int smsType, Object... params) throws RpcException;
	
	/**
	 * 发送国际短信.
	 * @param areaCode  国家编码
	 * @param mobile    手机号码
	 * @param smsType   短信类型
	 * @param params    参数
	 * @return
	 * @throws RpcException
	 */
	SmsDto post(String areaCode, String mobile, int smsType, Object... params) throws RpcException;
}
