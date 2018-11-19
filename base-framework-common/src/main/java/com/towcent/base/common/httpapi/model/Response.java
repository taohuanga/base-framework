/**
 * 
 */
package com.towcent.base.common.httpapi.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author shiwei
 *
 */
public class Response implements Serializable {

	/**
	 * {"error_response": {"code":"15","zh_desc":"不存在的方法名","en_desc":"Invalid Method"}}
	 */
	private static final long serialVersionUID = 1L;

	// 请求地址方法
	private String requestMethod;
	
	// 错误信息
	private ErrorResponse error_response;

	public ErrorResponse getError_response() {
		return error_response;
	}

	public void setError_response(ErrorResponse error_response) {
		this.error_response = error_response;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
		}
		return super.toString();
	}

	@Override
	public boolean equals(Object arg0) {
		StringBuffer methodName = new StringBuffer();
		Field[] srcfields = this.getClass().getDeclaredFields();
		Method method = null;
		Method method1 = null;
		for (Field field : srcfields) {
			if (field.getName().equals("serialVersionUID")) {
				continue;
			}
			try {
				methodName.delete(0, methodName.length());
				methodName.append("get");
				methodName
						.append(field.getName().substring(0, 1).toUpperCase());
				methodName.append(field.getName().substring(1));
				method = this.getClass().getMethod(methodName.toString());
				method1 = arg0.getClass().getMethod(methodName.toString());

				if (!method.getClass().equals(method1.getClass())) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
