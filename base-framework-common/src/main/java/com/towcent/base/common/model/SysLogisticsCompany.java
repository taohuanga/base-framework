package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-07-11 18:40:57
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysLogisticsCompany implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 主键Id.
     */
	private Integer id;
	
	/**
     * 物流公司编码.
     */
	private String companyNo;
	
	/**
     * 物流公司名称.
     */
	private String companyName;
	
	/**
     * 备注.
     */
	private String remarks;
	
	/**
     * 创建人.
     */
	private String createBy;
	
	/**
     * 创建时间.
     */
	private Date createDate;
	
	/**
     * 更新人.
     */
	private String updateBy;
	
	/**
     * 更新时间.
     */
	private Date updateDate;
	
	/**
     * 删除标记(0:正常 1:删除).
     */
	private String delFlag;
	
	
}