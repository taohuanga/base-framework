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
 * 
 * @author shiwei
 *
 */
public class RequestHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// xml 和 json（String） 默认
	private String requestDataType = "json";

	// 请求地址方法
	private String requestMethod;
	// 请求内容类型
	private String contentType = "application/json";
	// 请求内容编码
	private String contentEncoding = "UTF-8";

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestDataType() {
		return requestDataType;
	}

	public void setRequestDataType(String requestDataType) {
		this.requestDataType = requestDataType;
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
