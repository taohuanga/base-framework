package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Generator
 * @date 2016-03-29 19:41:44
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class NotifySms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id.
     */
    private Integer id;

    /**
     * 接收手机.
     */
    private String phone;

    /**
     * 短信内容.
     */
    private String content;

    /**
     * 短信类型.
     */
    private Byte smsType;

    /**
     * 状态状态(0:提交失败2:提交成功 3:发送成功).
     */
    private Byte smsStatus;

    /**
     * 创建时间.
     */
    private Date createDate;

    /**
     * 更新时间.
     */
    private Date updateDate;

    /**
     * 备注.
     */
    private String remarks;

    /**
     * 消息Id.
     */
    private String smsid;
    
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
    
    
}