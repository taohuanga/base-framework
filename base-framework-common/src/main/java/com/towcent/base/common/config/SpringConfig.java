/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : SpringConfig.java
 * @Package : com.towcent.base.common.config
 * @date : 2018年1月19日下午1:03:17
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SpringConfig 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *
 * @author huangtao
 * @date 2018年1月19日 下午1:03:17
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Component
@Setter @Getter
public class SpringConfig {
	
	@Value("${redis.prefix}")
	private String redisPrefix;
	
	@Value("${jdbc.type}")
	private String jdbcType;
	
	@Value("${image.url.header}")
	private String imageHeaderUrl;
	
	@Value("${push.production}")
	private boolean pushProduction;
	
	@Value("${push.client.appkey}")
	private String pushAppkey;
	
	@Value("${push.client.mastersecret}")
	private String pushSecret;
	
	/**
	 * tag或者alias
	 */
	@Value("${push.audience}")  
	private String audienceType;
}

	