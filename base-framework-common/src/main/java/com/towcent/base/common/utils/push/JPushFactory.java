package com.towcent.base.common.utils.push;

import com.towcent.base.common.config.SpringConfig;
import com.towcent.base.common.utils.SpringContextHolder;

import cn.jpush.api.JPushClient;

public class JPushFactory {
	
	/**
	 * 客户端推送实例
	 */
	private static JPushClient jPushClient;
	
	public static JPushClient getJPushClient() {
		if (null != jPushClient) {
			return jPushClient;
		}
		SpringConfig springConfig = SpringContextHolder.getBean(SpringConfig.class);
		String appKey = springConfig.getPushAppkey();
		String masterSecret = springConfig.getPushSecret();
		jPushClient = new JPushClient(masterSecret, appKey);
		return jPushClient;
	}
	
}
