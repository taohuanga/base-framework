/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : WxInstanceFactory.java
 * @Package : com.towcent.base.wx.service
 * @date : 2018年6月26日下午5:21:06
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.wx.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.towcent.base.common.model.SysWxConfig;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.manager.BaseCommonApi;
import com.towcent.base.wx.config.WxMpConfig;

/**
 * @ClassName: WxInstanceFactory 
 * @Description: 获取微信实例的工程方法
 *
 * @author huangtao
 * @date 2018年6月26日 下午5:21:06
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Component
public class WxInstanceFactory implements InitializingBean {

	// 微信公众号
	private static Map<Integer, WeixinService> instanceMaps = Maps.newHashMap();
	
	// 微信小程序
	private static Map<Integer, WeixinMaService> miniAppInstanceMaps = Maps.newHashMap();
	
	public WeixinService getInstance(Integer merchantId) {
		inspectExist(merchantId);
		return instanceMaps.get(merchantId);
	}
	
	public WeixinMaService getMiniAppInstance(Integer merchantId) {
		inspectExist(merchantId);
		return miniAppInstanceMaps.get(merchantId);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		BaseCommonApi commonApi = SpringContextHolder.getBean(BaseCommonApi.class);
		Map<String, Object> params = Maps.newHashMap();
		List<SysWxConfig> list = commonApi.getSysWxConfigListByParam(params);
		if (!CollectionUtils.isEmpty(list)) {
			WxMpConfig wxConfig = null;
			for (SysWxConfig config : list) {
				if (StringUtils.isBlank(config.getWxAppsecret())) {
					continue;
				}
				
				wxConfig = new WxMpConfig();
				wxConfig.setAppid(config.getAppid());
				wxConfig.setAppsecret(config.getWxAppsecret());
				wxConfig.setToken(config.getWxToken());
				wxConfig.setAesKey(config.getWxAeskey());
				
				instanceMaps.put(config.getMerchantId(), new WeixinService(wxConfig));
				miniAppInstanceMaps.put(config.getMerchantId(), new WeixinMaService(wxConfig));
			}
		}
	}
	
	/**
	 * 校验微信公众号实例是否存在.
	 * @Title inspectExist
	 * @param merchantId
	 */
	private void inspectExist(Integer merchantId) {
		try {
			if (!instanceMaps.containsKey(merchantId)) {
				BaseCommonApi commonApi = SpringContextHolder.getBean(BaseCommonApi.class);
				Map<String, Object> params = Maps.newHashMap();
				params.put("merchantId", merchantId);
				List<SysWxConfig> list = commonApi.getSysWxConfigListByParam(params);
				WxMpConfig wxConfig = null;
				if (!CollectionUtils.isEmpty(list)) {
					for (SysWxConfig config : list) {
						if (StringUtils.isBlank(config.getWxAppsecret())) {
							continue;
						}
						
						wxConfig = new WxMpConfig();
						wxConfig.setAppid(config.getAppid());
						wxConfig.setAppsecret(config.getWxAppsecret());
						wxConfig.setToken(config.getWxToken());
						wxConfig.setAesKey(config.getWxAeskey());
						
						instanceMaps.put(merchantId, new WeixinService(wxConfig));
						miniAppInstanceMaps.put(config.getMerchantId(), new WeixinMaService(wxConfig));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

	