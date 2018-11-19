package com.towcent.base.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 15:58:33
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class JsSysArea implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 区域编码.
     */
	private String areaCode;
	
	/**
     * 父级编号.
     */
	private String parentCode;
	
	/**
     * 所有父级编号.
     */
	private String parentCodes;
	
	/**
     * 本级排序号（升序）.
     */
	private BigDecimal treeSort;
	
	/**
     * 所有级别排序号.
     */
	private String treeSorts;
	
	/**
     * 是否最末级.
     */
	private String treeLeaf;
	
	/**
     * 层次级别.
     */
	private BigDecimal treeLevel;
	
	/**
     * 全节点名.
     */
	private String treeNames;
	
	/**
     * 区域名称.
     */
	private String areaName;
	
	/**
     * 区域类型.
     */
	private String areaType;
	
	/**
     * 状态（0正常 1删除 2停用）.
     */
	private String status;
	
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