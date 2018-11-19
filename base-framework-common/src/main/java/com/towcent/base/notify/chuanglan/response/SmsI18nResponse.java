package com.towcent.base.notify.chuanglan.response;

public class SmsI18nResponse {
	/**
	 * 响应时间
	 */
	private String code;
	/**
	 * 消息id
	 */
	private String msgid;
	/**
	 * 状态码说明（成功返回空）
	 */
	private String error;
	/**
	 * 状态码（详细参考提交响应状态码）
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "SmsSingleResponse [code=" + code + ", msgid=" + msgid + ", error=" + error + ", code=" + code
				+ "]";
	}
}
