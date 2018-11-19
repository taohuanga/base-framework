package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-03-24 10:13:24
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysAppFeedback implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 主键Id.
     */
	private Integer id;
	
	/**
     * 反馈账号.
     */
	private Integer userId;
	
	/**
     * 反馈内容.
     */
	private String content;
	
	/**
     * 手机型号.
     */
	private String phoneModel;
	
	/**
     * 系统版本.
     */
	private String sysVersion;
	
	/**
     * 手机操作系统(1:ios 2:安卓 3:h5).
     */
	private Byte sysType;
	
	/**
     * 应用版本.
     */
	private String appVersion;
	
	/**
     * App类别(1:医生端).
     */
	private Byte appType;
	
	/**
     * 反馈时间.
     */
	private Date createDate;
	
	/**
     * 反馈类别(1:内容意见 2:产品建议 3:技术问题 4:其它).
     */
	private String feedbackType;
	
	/**
     * 图片(多张图;分割).
     */
	private String picUrl;
	
	/**
     * 联系电话.
     */
	private String contact;
	
	/**
	 * 处理状态(0:未处理 1:已处理 2:不处理)
	 */
	private String status;
	
	/**
	 * 处理结果
	 */
	private String handleResult;
	
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
	
}