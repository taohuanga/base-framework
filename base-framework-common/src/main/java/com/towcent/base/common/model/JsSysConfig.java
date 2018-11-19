package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 11:31:24
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class JsSysConfig implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 编号.
     */
	private String id;
	
	/**
     * 名称.
     */
	private String configName;
	
	/**
     * 参数键.
     */
	private String configKey;
	
	/**
     * 参数值.
     */
	private String configValue;
	
	/**
     * 系统内置（1是 0否）.
     */
	private String isSys;
	
	/**
     * 创建者.
     */
	private String createBy;
	
	/**
     * 创建时间.
     */
	private Date createDate;
	
	/**
     * 更新者.
     */
	private String updateBy;
	
	/**
     * 更新时间.
     */
	private Date updateDate;
	
	/**
     * 备注信息.
     */
	private String remarks;
	
	
}