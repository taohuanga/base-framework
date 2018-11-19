package com.towcent.base.common.exception;

/**
 * 业务处理异常
 * <div style="color:red">
 * 请尽量使用带errorCode的构造ServiceException(String code,String msg,Throwable cause)
 * </div>.
 *
 * @author wei.b
 */
public class ServiceException extends Exception {
	
	private static final long serialVersionUID = -8336393496063368116L;
	
	private int errorCode;

	public ServiceException() {
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ServiceException(int code,String msg) {
		super(msg);
		this.errorCode=code;
	}

	public ServiceException(int code,String msg,Throwable cause){
		super(code+":"+msg, cause);
		this.errorCode=code;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

}
