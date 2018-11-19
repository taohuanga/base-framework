package com.towcent.base.notify.chuanglan.request;

public class SmsI18nRequest {
	
	private String account;
    private String password;
    private String msg;
    private String mobile;
    
	public SmsI18nRequest(String account, String password, String msg, String mobile) {
		this.account = account;
		this.password = password;
		this.msg = msg;
		this.mobile = mobile;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
