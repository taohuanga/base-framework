package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 09:41:48
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class JsSysDictType implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 编号.
     */
	private String id;
	
	/**
     * 字典名称.
     */
	private String dictName;
	
	/**
     * 字典类型.
     */
	private String dictType;
	
	/**
     * 是否系统字典.
     */
	private String isSys;
	
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