package com.towcent.base.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class ApiDocParamIn implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 主键Id.
     */
	private Integer id;
	
	/**
     * 参数名称.
     */
	private String paramName;
	
	/**
     * 参数描述.
     */
	private String paramDesc;
	
	/**
     * 参数类型(String|Integer|Double|Date).
     */
	private String paramType;
	
	/**
     * 取值说明.
     */
	private String valDeclare;
	
	/**
     * 是否必填项(0:否 1:是).
     */
	private String required;
	
	/**
     * 正则表达式.
     */
	private String regular;
	
	/**
     * 接口Id.
     */
	private Integer interfaceId;
	
	
}