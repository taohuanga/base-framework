package com.towcent.base.common.utils.push;

import java.util.Map;

import com.google.common.collect.Maps;
import com.towcent.base.common.config.SpringConfig;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.common.utils.StringUtils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 
 * @author huangtao
 *
 */
public class PayloadBuild {
	
	/**
	 * 判断系统是否使用别名进行推送（默认使用tag进行推送）.
	 * @Title isAliasAlert
	 * @param audienceType
	 * @return
	 */
	private static boolean isAliasAlert(String audienceType) {
		if (StringUtils.isBlank(audienceType)) {
			return false;
		}
		return StringUtils.equals(audienceType, "alias");
	}
	
	private static boolean isAliasAlert(SpringConfig config) {
		if (null == config) {
			return false;
		}
		return isAliasAlert(config.getAudienceType());
	}
	
	/**
	 * 构建一个通知所有平台的，所有设备的推送对象
	 * @param title 通知标题
	 * @param content 通知内容
	 * @param extraMap 扩展参数
	 * @return
	 */
	public static PushPayload buildPushAllPayload(String title, String content, Map<String, String> extraMap) {
		if (null == extraMap) {
			extraMap = Maps.newHashMap();
		}
		SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
		IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody(title, null, content).build();
		return PushPayload.newBuilder()
	        .setPlatform(Platform.all())  
	        .setAudience(Audience.all())  
	        .setNotification(Notification.newBuilder()  
	                .addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).addExtras(extraMap) 
	                        .setTitle(title).build())  
	                .addPlatformNotification(IosNotification.newBuilder().setAlert(iosAlert).addExtras(extraMap)  
	                        .incrBadge(1).build())  
	                .build())
	        // .setMessage(Message.newBuilder().setMsgContent(content).addExtras(extraMap).build())
	        .setOptions(Options.newBuilder().setApnsProduction(config.isPushProduction()).build())
	        .build();
	}
	
	/**
	 * 因为IOS和Android通知的显示形式不一样，所以我们这两者分开写，分别单独推送。
	 * 根据别名、内容、标题，构建一个推送IOS平台的推送对象
	 * @param alias 别名
	 * @param title 通知标题
	 * @param content 通知内容
	 * @return
	 */
	public static PushPayload buildIOSPayload(String alias, String title, String content) {
		SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
		IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody(title, null, content).build();
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(isAliasAlert(config) ? Audience.alias(alias) : Audience.tag(alias))
				.setNotification(Notification.newBuilder()  
		                .addPlatformNotification(IosNotification.newBuilder().setAlert(iosAlert).incrBadge(1).build()).build())
				.setOptions(Options.newBuilder().setApnsProduction(config.isPushProduction()).build())
				.build();
	}
	
	/**
	 * 根据别名、内容、标题，构建一个推送Android平台的推送对象
	 * @param alias 别名
	 * @param title 通知标题
	 * @param content 通知内容
	 * @return
	 */
	public static PushPayload buildAndroidPayload(String alias,
			String title, String content) {
		SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(isAliasAlert(config) ? Audience.alias(alias) : Audience.tag(alias))
				.setNotification(Notification.newBuilder()
				.setAlert(content).addPlatformNotification(AndroidNotification
				.newBuilder().setTitle(title).build()).build())
				.build();
	}
	
	public static PushPayload buildPushObjectAndroidAndIos(String alias, String title, String content) {  
		Map<String, String> extraMap = Maps.newHashMap();
        return buildPushObjectAndroidAndIos(alias, title, content, extraMap);  
    } 
	
    public static PushPayload buildPushObjectAndroidAndIos(String alias, String title, String content, Map<String, String> extraMap) {  
    	SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
    	IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody(title, null, content).build();
    	return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())  
                .setAudience(isAliasAlert(config) ? Audience.alias(alias) : Audience.tag(alias))  
                .setNotification(Notification.newBuilder()  
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).addExtras(extraMap)    
                                .setTitle(title).build())
                        .addPlatformNotification(IosNotification.newBuilder().setAlert(iosAlert).addExtras(extraMap)  
                                .incrBadge(1).build())  
                        .build())
                // .setMessage(Message.newBuilder().setMsgContent(content).addExtras(extraMap).build())
                .setOptions(Options.newBuilder().setApnsProduction(config.isPushProduction()).build())
                .build();  
    } 
    
	public static void main(String[] args) {
		String appKey = "";
		String masterSecret = "";
		PushPayload ppla = buildAndroidPayload("", "", "");
		
		try {
			JPushClient jpushClient = new JPushClient(masterSecret, appKey);
			jpushClient.sendPush(ppla);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
