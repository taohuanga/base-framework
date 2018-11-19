package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-03-24 10:53:20
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class NotifyMail implements Serializable {

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
     * 类型（数据字典）1:.
     */
	private String noticeType;
	
	/**
     * 会员Id.
     */
	private Integer userId;
	
	/**
     * App类别(0:承运商 1:货主).
     */
	private Byte appType;
	
	/**
     * 创建时间.
     */
	private Date createDate;
	
	/**
     * 业务编码(订单号).
     */
	private String bizNo;
	
	/**
     * 是否已读(0:未读 1:已读).
     */
	private Byte isRead;
	
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
}