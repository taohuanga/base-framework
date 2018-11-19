package com.towcent.base.wx.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Binary Wang
 *
 */
// @Configuration
@Setter @Getter
public class WxMpConfig {
  // @Value("${wx_token}")
  private String token;

  // @Value("${wx_appid}")
  private String appid;

  // @Value("${wx_appsecret}")
  private String appsecret;

  // @Value("${wx_aeskey}")
  private String aesKey;

  
}
