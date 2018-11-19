package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Generator
 * @date 2017-11-16 11:09:16
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysAppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id.
     */
    private Integer id;
    
    /**
     * App类型(0:客户端)  -- 默认0.
     */
    private Byte appType;

    /**
     * 操作系统类型(1:ios 2:安卓 3:pad 4:H5)  -- 默认1.
     */
    private Byte sysType;
    
    /**
     * 当前版本号.
     */
    private Integer currentVersion;

    /**
     * 兼容版本号(2:不需要强制 1:需要强制升级).
     */
    private Integer compatibleVersion;

    /**
     * 版本号名称(1.1.2).
     */
    private String versionName;
    
    /**
     * 更新记录.
     */
    private String updateContent;

    /**
     * 下载地址.
     */
    private String url;

    /**
     * 唯一码.
     */
    private String md5;
    
    /**
     * 创建人.
     */
    private String createBy;

    /**
     * 创建时间.
     */
    private Date createDate;

    /**
     * 更新人.
     */
    private String updateBy;

    /**
     * 更新时间.
     */
    private Date updateDate;

    /**
     * 备注.
     */
    private String remark;
    
    /**
     * 商户Id  -- 默认0.
     */
    private Integer merchantId;
    
    private String EnforceFlag = "2";
    
}