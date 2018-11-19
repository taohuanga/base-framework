package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Generator
 * @date 2016-03-25 20:15:51
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysAppSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id.
     */
    private Integer id;

    /**
     * tokenId.
     */
    private String tokenId;
    
    /**
     * 账号Id
     */
    private Integer userId;

    /**
     * 账号(手机号码).
     */
    private String account;

    /**
     * app类别(1:客户端 2:伙伴端).
     */
    private Integer appType;

    /**
     * 移动设备号.
     */
    private String deviceNo;

    /**
     * 设备token(ios推送).
     */
    private String deviceToken;

    /**
     * 手机操作系统(1:ios 2:安卓 3:pad 4:h5) -- 默认1.
     */
    private Byte sysType;

    /**
     * 手机型号.
     */
    private String phoneModel;

    /**
     * App应用版本（current_version）.
     */
    private Integer appVersion;
    
    /**
     * 失效时间.
     */
    private Date failureTime;

    /**
     * 更新时间.
     */
    private Date updateDate;
    
    /**
     * 删除标记(0:正常 1:删除).
     */
    private String delFlag;
    
    /**
     * 微信openid.
     */
    private String openId;

    /**
     * 商户Id  -- 默认0.
     */
    private Integer merchantId;
}