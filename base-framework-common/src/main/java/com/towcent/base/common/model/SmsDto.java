/**
 * 
 */
package com.towcent.base.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author huangtao
 *
 */
@XmlRootElement(name="SubmitResult", namespace="http://106.ihuyi.cn/")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String NAMESPACE = "http://106.ihuyi.cn/";
	
	@XmlElement(name = "code", namespace = NAMESPACE)
	private Integer code;
	
	@XmlElement(name = "msg", namespace = NAMESPACE)
	private String msg;
	
	@XmlElement(name = "smsid", namespace = NAMESPACE)
	private String smsid;
	
	private String smsContent;  // 发送的短信内容
	
	public SmsDto() {
		super();
	}
	
	public SmsDto(Integer code, String msg, String smsid) {
		super();
		this.code = code;
		this.msg = msg;
		this.smsid = smsid;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSmsid() {
		return smsid;
	}
	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	@Override
	public String toString() {
		return "SmsDto [code=" + code + ", msg=" + msg + ", smsid=" + smsid
				+ "]";
	}
	
}
