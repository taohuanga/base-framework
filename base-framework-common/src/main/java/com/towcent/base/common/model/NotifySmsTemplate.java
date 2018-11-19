package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Generator
 * @date 2017-05-11 16:33:47
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class NotifySmsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    private Integer id;

    /**
     * 模板类别(1:找回密码 2:用户确认).
     */
    private Byte smsType;

    /**
     * 模板内容.
     */
    private String content;

    /**
     * 创建时间.
     */
    private Date createDate;

    /**
     * 创建人.
     */
    private String createBy;

    /**
     * 更新时间.
     */
    private Date updateDate;

    /**
     * 更新人.
     */
    private String updateBy;

    /**
     * 第三方模板Id.
     */
    private String smsTemplateId;

    /**
     * 短信签名.
     */
    private String signature;

    /**
     * 参数列表(,分割).
     */
    private String paramStr;

    /**
     * 是否有验证码(0:否 1:是).
     */
    private String isSecurityCode;

    /**
     * 验证码有效时长(分钟).
     */
    private Integer validTime;
    
	/**
	 * 商户Id.
	 */
	private Integer merchantId;
    
    
}