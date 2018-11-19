package com.towcent.base.common.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SmsParamDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String areaCode;
	
	@NotBlank(message="手机号码不能为空")
	private String mobile;
	
	@NotNull(message="短信类型不能为空")
	private Byte smsType;
	
	/** 短信验证码 */
	private String securityCode;
	
	/** 短信参数 */
	private Object[] smsParams;
	
	public SmsParamDto() {
		super();
	}
	
	public SmsParamDto(String mobile, Byte smsType) {
		super();
		this.mobile = mobile;
		this.smsType = smsType;
	}
	
	public SmsParamDto(String mobile, Byte smsType, String securityCode) {
		super();
		this.mobile = mobile;
		this.smsType = smsType;
		this.securityCode = securityCode;
	}
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Byte getSmsType() {
		return smsType;
	}

	public void setSmsType(Byte smsType) {
		this.smsType = smsType;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Object[] getSmsParams() {
		return smsParams;
	}

	public void setSmsParams(Object[] smsParams) {
		this.smsParams = smsParams;
	}

}
