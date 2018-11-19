package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Generator
 * @date 2017-11-15 10:19:54
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class SysImageConf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id.
     */
    private Integer id;

    /**
     * 图片类型(0:头像).
     */
    private Integer imageType;

    /**
     * 图片相对路径(图片服务器：app/icon/).
     */
    private String imagePath;

    /**
     * 子目录格式(yyyyMM|yyyyMMdd).
     */
    private String subDirFormat;

    /**
     * 原图是否需要压缩(1:是 0:否).
     */
    private String isOriginalCompress;

    /**
     * 原图尺寸(1024X1024).
     */
    private String originalSize;

    /**
     * 是否需要缩略图(1:是 0:否).
     */
    private String isThumb;

    /**
     * 缩略图尺寸(230X230).
     */
    private String thumbSize;

    /**
     * 更新时间.
     */
    private Date updateDate;

	/**
	 * 商户Id.
	 */
	private Integer merchantId;
    
    
}