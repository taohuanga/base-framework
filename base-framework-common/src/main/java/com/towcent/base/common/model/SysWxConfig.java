package com.towcent.base.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-06-26 18:31:02
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysWxConfig implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 主键Id.
     */
	private Integer id;
	
	/**
     * 应用id.
     */
	private String appid;
	
	/**
     * 微信公众号秘钥.
     */
	private String wxAppsecret;
	
	/**
     * 微信公众号Token.
     */
	private String wxToken;
	
	/**
     * 微信消息加解密秘钥.
     */
	private String wxAeskey;
	
	/**
     * 公众号备注.
     */
	private String wxRemark;
	
	/**
     * 是否是服务号(字典yes_no  1:是 0:否).
     */
	private String isService;
	
	/**
     * 商户Id.
     */
	private Integer merchantId;
	
	
}