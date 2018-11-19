package com.towcent.base.common.model;

import java.io.Serializable;

public class PushMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * App类型(0:承运商 1:货主)
	 */
	private Byte appType;

	/**
	 * 推送账号(承运商、货主)
	 */
	private String account;
	
	/**
	 * deviceToken
	 */
	private String token;
	
	private int badge;
	
	private String target;
	
	private String title;
	
	public Byte getAppType() {
		return appType;
	}

	public void setAppType(Byte appType) {
		this.appType = appType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getBadge() {
		return badge;
	}

	public void setBadge(int badge) {
		this.badge = badge;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
