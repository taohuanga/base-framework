package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-03-24 10:51:53
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 主键id.
     */
	private Integer id;
	
	/**
     * 标题.
     */
	private String title;
	
	/**
     * 内容.
     */
	private String content;
	
	/**
     * 类型（数据字典）.
     */
	private String noticeType;
	
	/**
     * App类别(0:承运商 1:货主).
     */
	private Byte appType;
	
	/**
     * 创建时间.
     */
	private Date createDate;
	
	/**
     * 创建人.
     */
	private String createBy;
	
	/**
     * 删除标记(0:正常1:删除).
     */
	private String delFlag;
	
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
}