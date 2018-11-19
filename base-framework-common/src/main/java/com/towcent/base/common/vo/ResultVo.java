package com.towcent.base.common.vo;

import java.io.Serializable;

import com.towcent.base.common.constants.BaseConstant;

/**
 * 
 * 公共返回结果Vo
 * 
 * @author huangtao
 * @date 2015年6月24日 下午4:48:48
 * @version 0.1.0 
 */
public class ResultVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 数据
	 */
	public Object data;
	
	/**
	 * 返回码
	 */
	public String code = BaseConstant.E_000;
	
	/**
	 * 错误描述信息
	 */
	public String errorMessage;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
