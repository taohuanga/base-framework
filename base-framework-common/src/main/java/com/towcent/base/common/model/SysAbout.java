package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author licheng
 * @date 2018-02-01 16:48:45
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysAbout implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * ID.
     */
	private Integer id;
	
	/**
     * app类别(1:医生端).
     */
	private Byte appType;
	
	/**
     * logo.
     */
	private String logo;
	
	/**
     * 名称.
     */
	private String appName;
	
	/**
     * 关于我们内容.
     */
	private String about;
	
	/**
     * 版权.
     */
	private String copyright;
	
	/**
     * 软件服务协议.
     */
	private String serviceAgreement;
	
	/**
     * 隐私政策.
     */
	private String privacyPolicy;
	
	/**
     * 用户须知.
     */
	private String userNotes;
	
	/**
     * 创建人.
     */
	private String createBy;
	
	/**
     * 创建时间.
     */
	private Date createDate;
	
	/**
     * 修改人.
     */
	private String updateBy;
	
	/**
     * 修改日期.
     */
	private Date updateDate;
	
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
}