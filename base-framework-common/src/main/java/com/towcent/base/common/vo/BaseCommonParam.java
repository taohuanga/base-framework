package com.towcent.base.common.vo;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * protal公共查询参数
 * 
 * @author huangtao
 * @date 2015年6月25日 下午1:38:21
 * @version 0.1.0 
 */
@Setter @Getter
public class BaseCommonParam extends PageVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 设备类型 (1:IOS 2:Android 3:pad 4:h5)
	 */
	@NotNull(message="设备类型不能为空")
	@Min(value=1, message="设备类型只能为(1:Ios 2:Android 3:pad 4:h5)")
	@Max(value=4, message="设备类型只能为(1:Ios 2:Android 3:pad 4:h5)")
	private Byte sysType;
	
	/**
	 * APP应用类型 (0:客户端 1: 2: 3:)
	 */
	@NotNull(message="App应用类型不能为空")
	@Min(value=0, message="App应用类型只能为(0:客户端 1: 2: 3:)")
	@Max(value=3, message="App应用类型只能为(0:客户端 1: 2: 3:)")
	protected Byte appType = 0;
	
	/**
	 * 移动设备号
	 */
	@NotBlank(message="移动设备号不能为空.")
	private String deviceNo;
	
	/**
	 * 设备token (IOS推送)  -- 走三方推送  （暂时无用）
	 */
	private String deviceToken;
	
	/**
	 * 手机型号
	 */
	// @NotBlank(message="手机型号不能为空.")
	private String phoneModel;
	
	/**
	 * APP应用版本号(从1开始依次累加)
	 */
	@NotNull(message="App应用版本号不能为空")
	@Min(value = 1, message = "App应用版本号需要>=1开始")
	private Integer versionNo;
	
	/**
	 * APP应用版本号名称(例如 0.0.1)
	 */
	@NotBlank(message="app应用版本不能为空.")
	private String appVersion = "0.0.1";
	
	/**
	 * 移动操作系统版本(IOS版本或者Android版本)
	 */
	@NotBlank(message="移动操作系统版本不能为空.")
	private String osVersion = "0.0.1";
	
	/**
	 * appKey
	 */
	@NotBlank(message="appKey不能为空.")
	private String appKey;
	
	/**
	 * 时间戳
	 */
	@NotBlank(message="时间戳不能为空.")
	private String timestamp;
	
	/**
	 * 登录标识
	 */
	private String tokenId;
	
}
