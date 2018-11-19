package com.towcent.base.wx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.towcent.base.common.redis.RedisTemplateExt;

import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import redis.clients.jedis.JedisPool;

/**
 * 重写redis配置
 * 
 * @author Administrator
 *
 */
public class BaseMpInRedisConfigStorage extends WxMpInRedisConfigStorage {
	
	public BaseMpInRedisConfigStorage(JedisPool jedisPool) {
		super(jedisPool);
	}
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private RedisTemplateExt<String, Object> redisTemplateExt;
	private String accessTokenKey;
	private String jsapiTicketKey;
	private String cardapiTicketKey;

	public void setRedisTemplateExt(RedisTemplateExt<String, Object> redisTemplateExt) {
		logger.info("----初始化:" + redisTemplateExt);
		this.redisTemplateExt = redisTemplateExt;
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

	@Override
	public String getJsapiTicket() {
		return (String) redisTemplateExt.get(jsapiTicketKey);
	}

	@Override
	public boolean isJsapiTicketExpired() {
		return !redisTemplateExt.exists(jsapiTicketKey);
	}
	
	@Override
	public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
		redisTemplateExt.set(jsapiTicketKey, jsapiTicket, (expiresInSeconds / 60) - 1);
	}

	@Override
	public void expireJsapiTicket() {
		redisTemplateExt.expire(jsapiTicketKey, 0);
	}
	
	@Override
	public String getCardApiTicket() {
		return (String) redisTemplateExt.get(cardapiTicketKey);
	}

	@Override
	public boolean isCardApiTicketExpired() {
		return !redisTemplateExt.exists(cardapiTicketKey);
	}

	@Override
	public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
		redisTemplateExt.set(cardapiTicketKey, cardApiTicket, (expiresInSeconds / 60) - 1);
	}
	
	@Override
	public void expireCardApiTicket() {
		redisTemplateExt.expire(cardapiTicketKey, 0);
	}

}
