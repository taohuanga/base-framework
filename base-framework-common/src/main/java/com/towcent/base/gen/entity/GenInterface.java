package com.towcent.base.gen.entity;

import java.util.List;

/**
 * @author Jerry
 *
 */
public class GenInterface {
	private String interNo;			              // 接口编号
	private String interName;		              // 接口名称	
	private String funDesc;			              // 功能简述
	private String reqType;			              // 请求类型
	private String interUrl;		              // 接口URL
	private List<GenParamEntry> enParamList;	  // 入参列表
	private String outParamType;				  // 出参类型
	private List<GenParamEntry> outParamList;	  // 出参列表
	private String secOutParamType;				  // 二级出参类型
	private List<GenParamEntry> secOutParamList;  // 二级出参列表
	private String entryName;		              // 对应实体类
	
	private String packageName;                   // 基础包名
	
	private String moduleName;                    // 模块名
	
	private String className;                     // 类名
	private String dClassName;                    // 类名(首字母大写)
	
	private String functionName;                  // 方法名
	private String dFunctionName;                 // 方法名(首字母大写)
	
	private String functionVersion;               // 版本号
	
	public String getInterNo() {
		return interNo;
	}
	public void setInterNo(String interNo) {
		this.interNo = interNo;
	}
	
	public String getInterName() {
		return interName;
	}
	public void setInterName(String interName) {
		this.interName = interName;
	}
	
	public String getFunDesc() {
		return funDesc;
	}
	public void setFunDesc(String funDesc) {
		this.funDesc = funDesc;
	}
	
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	
	public String getInterUrl() {
		return interUrl;
	}
	public void setInterUrl(String interUrl) {
		this.interUrl = interUrl;
	}
	
	public List<GenParamEntry> getEnParamList() {
		return enParamList;
	}
	public void setEnParamList(List<GenParamEntry> enParamList) {
		this.enParamList = enParamList;
	}
	
	public String getOutParamType() {
		return outParamType;
	}
	public void setOutParamType(String outParamType) {
		this.outParamType = outParamType;
	}
	
	public List<GenParamEntry> getOutParamList() {
		return outParamList;
	}
	public void setOutParamList(List<GenParamEntry> outParamList) {
		this.outParamList = outParamList;
	}
	
	public String getSecOutParamType() {
		return secOutParamType;
	}
	public void setSecOutParamType(String secOutParamType) {
		this.secOutParamType = secOutParamType;
	}
	
	public List<GenParamEntry> getSecOutParamList() {
		return secOutParamList;
	}
	public void setSecOutParamList(List<GenParamEntry> secOutParamList) {
		this.secOutParamList = secOutParamList;
	}
	
	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getdClassName() {
		return dClassName;
	}
	public void setdClassName(String dClassName) {
		this.dClassName = dClassName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getdFunctionName() {
		return dFunctionName;
	}
	public void setdFunctionName(String dFunctionName) {
		this.dFunctionName = dFunctionName;
	}
	public String getFunctionVersion() {
		return functionVersion;
	}
	public void setFunctionVersion(String functionVersion) {
		this.functionVersion = functionVersion;
	}
	
}
