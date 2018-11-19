package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.List;

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
public class ApiDocMain implements Serializable {

    private static final long serialVersionUID = 1L;
	
	/**
     * 主键Id.
     */
	private Integer id;
	
	/**
     * 接口组(数据字典interface_group).
     */
	private String interfaceGroup;
	
	/**
     * 接口编码（1.1.0）第一位是组，第二位是模块，第三位是序号.
     */
	private String interfaceNo;
	
	/**
     * 接口名称.
     */
	private String interfaceName;
	
	/**
     * 接口描述.
     */
	private String interfaceDesc;
	
	/**
     * 请求类型(GET|POST).
     */
	private String requestType;
	
	/**
     * 接口URL.
     */
	private String interfaceUrl;
	
	/**
     * 出参类型(void|object|list|page).
     */
	private String paramOutType;
	
	/**
     * 是否需要验证登录(0:否 1:是).
     */
	private String isLogin;
	
	
	// 扩展参数
	// 入参
	private List<ApiDocPublicParam> publicParams;
	private List<ApiDocParamIn> paramIns;
	
	// 出参
	private List<ApiDocParamOut> paramOuts;
}