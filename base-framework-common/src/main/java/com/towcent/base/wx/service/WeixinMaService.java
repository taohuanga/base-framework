/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : WeixinMaService.java
 * @Package : com.towcent.base.wx.service
 * @date : 2018年5月15日下午3:27:19
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.wx.service;

import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.wx.config.BaseMaInRedisConfigStorage;
import com.towcent.base.wx.config.WxMpConfig;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;

/**
 * @ClassName: WeixinMaService 
 * @Description: 微信小程序
 *
 * @author huangtao
 * @date 2018年5月15日 下午3:27:19
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
// @Service
public class WeixinMaService extends WxMaServiceImpl {

	private RedisTemplateExt<String, Object> redisTemplateExt;
	
	@SuppressWarnings("unchecked")
	public WeixinMaService(WxMpConfig wxConfig) {
		this.redisTemplateExt = SpringContextHolder.getBean(RedisTemplateExt.class);
		
		// 基于redis存储公众号配置
		final BaseMaInRedisConfigStorage config = new BaseMaInRedisConfigStorage();
		config.setRedisTemplateExt(redisTemplateExt);
		
		config.setAppid(wxConfig.getAppid());// 设置微信公众号的appid
		config.setSecret(wxConfig.getAppsecret());// 设置微信公众号的app corpSecret
		config.setToken(wxConfig.getToken());// 设置微信公众号的token
		config.setAesKey(wxConfig.getAesKey());// 设置消息加解密密钥
		super.setWxMaConfig(config);
		
	}
}

	