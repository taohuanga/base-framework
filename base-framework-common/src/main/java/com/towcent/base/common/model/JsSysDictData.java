package com.towcent.base.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 09:41:10
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class JsSysDictData implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 字典编码.
     */
	private String dictCode;
	
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
     * 字典标签.
     */
	private String dictLabel;
	
	/**
     * 字典键值.
     */
	private String dictValue;
	
	/**
     * 字典类型.
     */
	private String dictType;
	
	/**
     * 系统内置（1是 0否）.
     */
	private String isSys;
	
	/**
     * 字典描述.
     */
	private String description;
	
	/**
     * css样式（如：color:red).
     */
	private String cssStyle;
	
	/**
     * css类名（如：red）.
     */
	private String cssClass;
	
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
	
	/**
     * 租户代码.
     */
	private String corpCode;
	
	/**
     * 租户名称.
     */
	private String corpName;
	
	/**
     * 扩展 String 1.
     */
	private String extendS1;
	
	/**
     * 扩展 String 2.
     */
	private String extendS2;
	
	/**
     * 扩展 String 3.
     */
	private String extendS3;
	
	/**
     * 扩展 String 4.
     */
	private String extendS4;
	
	/**
     * 扩展 String 5.
     */
	private String extendS5;
	
	/**
     * 扩展 String 6.
     */
	private String extendS6;
	
	/**
     * 扩展 String 7.
     */
	private String extendS7;
	
	/**
     * 扩展 String 8.
     */
	private String extendS8;
	
	/**
     * 扩展 Integer 1.
     */
	private BigDecimal extendI1;
	
	/**
     * 扩展 Integer 2.
     */
	private BigDecimal extendI2;
	
	/**
     * 扩展 Integer 3.
     */
	private BigDecimal extendI3;
	
	/**
     * 扩展 Integer 4.
     */
	private BigDecimal extendI4;
	
	/**
     * 扩展 Float 1.
     */
	private BigDecimal extendF1;
	
	/**
     * 扩展 Float 2.
     */
	private BigDecimal extendF2;
	
	/**
     * 扩展 Float 3.
     */
	private BigDecimal extendF3;
	
	/**
     * 扩展 Float 4.
     */
	private BigDecimal extendF4;
	
	/**
     * 扩展 Date 1.
     */
	private Date extendD1;
	
	/**
     * 扩展 Date 2.
     */
	private Date extendD2;
	
	/**
     * 扩展 Date 3.
     */
	private Date extendD3;
	
	/**
     * 扩展 Date 4.
     */
	private Date extendD4;
	
	
}