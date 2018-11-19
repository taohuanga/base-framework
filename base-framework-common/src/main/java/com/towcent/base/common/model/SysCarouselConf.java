package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author licheng
 * @date 2018-04-18 11:11:48
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysCarouselConf implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * id.
     */
	private Integer id;
	
	/**
     * 轮播图类型(数据字典:carousel_type).
     */
	private String carouselType;
	
	/**
     * 标题.
     */
	private String title;
	
	/**
     * 图片地址.
     */
	private String url;
	
	/**
     * 是否是链接(1:是 0:不是).
     */
	private String isHref;
	
	/**
     * 目标链接.
     */
	private String targetUrl;
	
	/**
     * 排序.
     */
	private Integer sort;
	
	/**
     * 备注.
     */
	private String remark;
	
	/**
     * 创建者.
     */
	private Integer createBy;
	
	/**
     * 创建时间.
     */
	private Date createDate;
	
	/**
     * 更新者.
     */
	private Integer updateBy;
	
	/**
     * 更新时间.
     */
	private Date updateDate;
	
	/**
     * 删除标记(0:正常1:删除).
     */
	private String delFlag;
	
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
}