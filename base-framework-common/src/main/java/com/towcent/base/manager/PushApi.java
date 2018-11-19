package com.towcent.base.manager;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.JPushDto;
import com.towcent.base.common.model.PushMessage;

/**
 * 推送接口
 * @author huangtao
 *
 */
public interface PushApi {
	
	/**
	 * 推送消息(自己实现的推送)
	 * @param message
	 * @throws RpcException
	 */
	void pushMsg(PushMessage message) throws RpcException;
	
	/**
	 * 推送所有设备<br>
	 * 极光推送
	 * @param dto
	 * @throws RpcException
	 */
	void jpushAll(JPushDto dto) throws RpcException;
	
	/**
	 * 单点推送<br>.
	 * 极光推送
	 * @Title jpushMsg
	 * @param dto
	 * @throws RpcException
	 */
	void jpushMsg(JPushDto dto) throws RpcException;
}
