package com.towcent.base.wx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.towcent.base.common.redis.RedisTemplateExt;

import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;

/**
 * 重写redis配置
 * 
 * @author Administrator
 *
 */
public class BaseMaInRedisConfigStorage extends WxMaInMemoryConfig {
	
	protected final static String ACCESS_TOKEN_KEY = "wechat_access_token_miniapp_";
	protected final static String JSAPI_TICKET_KEY = "wechat_jsapi_ticket_miniapp_";
	protected final static String CARDAPI_TICKET_KEY = "wechat_cardapi_ticket_miniapp_";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private RedisTemplateExt<String, Object> redisTemplateExt;
	
    protected String accessTokenKey;

    protected String jsapiTicketKey;

    protected String cardapiTicketKey;
	
	public void setRedisTemplateExt(RedisTemplateExt<String, Object> redisTemplateExt) {
		logger.info("----初始化:" + redisTemplateExt);
		this.redisTemplateExt = redisTemplateExt;
	}
	
	/**
	 * 每个公众号生成独有的存储key
	 *
	 * @param appId
	 */
	@Override
	public void setAppid(String appid) {
		super.setAppid(appid);
		this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appid);
		this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appid);
		this.cardapiTicketKey = CARDAPI_TICKET_KEY.concat(appid);
	}
	
	@Override
	public String getAccessToken() {
		logger.info("1.getAccessToken方法:" + accessTokenKey);
		return (String) redisTemplateExt.get(accessTokenKey);
	}

	@Override
	public boolean isAccessTokenExpired() {
		logger.info("2.isAccessTokenExpired方法:" + accessTokenKey);
		return !redisTemplateExt.exists(accessTokenKey);
	}

	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		logger.info("3.updateAccessToken方法:token=" + accessToken + "过期时间:" + expiresInSeconds);
		redisTemplateExt.set(accessTokenKey, accessToken, (expiresInSeconds / 60) - 1);
	}

	@Override
	public void expireAccessToken() {
		logger.info("4.expireAccessToken方法:" + accessTokenKey);
		redisTemplateExt.expire(accessTokenKey, 0);
	}

	

}
