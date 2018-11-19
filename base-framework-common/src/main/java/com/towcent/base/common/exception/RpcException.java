package com.towcent.base.common.exception;

public class RpcException extends Exception {

	private static final long serialVersionUID = 356381812673914209L;
	
	private int errorCode;
	
	private String projectName;

	public RpcException(String projectName,String msg) {
		super(msg);
		this.projectName=projectName;
	}

	public RpcException(String projectName,Throwable cause) {
		super(cause);
		this.projectName=projectName;
	}

	public RpcException(String projectName,String msg, Throwable cause) {
		super(msg, cause);
		this.projectName=projectName;
	}

	public RpcException(String projectName,int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
		this.projectName=projectName;
	}
	
	public RpcException(int errorCode,String msg,String projectName, Throwable cause) {
		super(msg,cause);
		this.errorCode = errorCode;
		this.projectName = projectName;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getProjectName() {
		return projectName;
	}
}
