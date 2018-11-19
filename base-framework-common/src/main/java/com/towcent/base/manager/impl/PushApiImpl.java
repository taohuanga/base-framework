package com.towcent.base.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JPushDto;
import com.towcent.base.common.model.PushMessage;
import com.towcent.base.common.utils.push.JPushFactory;
import com.towcent.base.common.utils.push.PayloadBuild;
import com.towcent.base.manager.PushApi;
import com.towcent.base.service.BaseService;
import com.towcent.base.service.SysAppSessionService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Service
public class PushApiImpl extends BaseService implements PushApi {
	
	@Resource 
	private SysAppSessionService sessionApi;
	
	@Override
	public void pushMsg(PushMessage message) throws RpcException {
		message.setTarget("0");
		message.setBadge(1);
		
//		try {
			List<String> tokens = null; // sessionApi.getAppTokenListByParam(message.getAccount(), message.getAppType());
			if (!CollectionUtils.isEmpty(tokens)) {
				for (String token : tokens) {
					message.setToken(token);
					// ChatUtil.pushMessage4Ios(message);					
				}
			}
//		} catch (ServiceException e) {
//			logger.error("", e);
//			throw new RpcException("", e);
//		}
	}
	
	@Override
	public void jpushAll(JPushDto dto) throws RpcException {
		try {
			PushPayload payload = PayloadBuild.buildPushAllPayload(dto.getTitle(), dto.getContent(), dto.getExtraMap());
			PushResult result = JPushFactory.getJPushClient().sendPush(payload);
			logger.info("推送结果:{}.", result);
		} catch (APIConnectionException e) {
			logger.error("", e);
		} catch (APIRequestException e) {
			logger.error("", e);
		}
	}
	
	@Override
	public void jpushMsg(JPushDto dto) throws RpcException {
		try {
			PushPayload payload = PayloadBuild.buildPushObjectAndroidAndIos(
					dto.getUserId() + "", dto.getTitle(), dto.getContent(), dto.getExtraMap());
			JPushClient client = JPushFactory.getJPushClient();
			PushResult result = client.sendPush(payload);
			logger.info("推送结果:{}.", result);
		} catch (APIConnectionException e) {
			logger.error("", e);
		} catch (APIRequestException e) {
			logger.error("", e);
		}
		
	}

}
