package com.towcent.base.gen.entity;

public class GenParamEntry {

	private String fieldName;
	private String fieldType;
	private String isNotNull;
	private String description;
	private String maxLength;
	private String simpleJavaType;
	
	private boolean notBaseField = false; // 是否为基础类型
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getIsNotNull() {
		return isNotNull;
	}
	public void setIsNotNull(String isNotNull) {
		this.isNotNull = isNotNull;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSimpleJavaType() {
		return simpleJavaType;
	}
	public void setSimpleJavaType(String simpleJavaType) {
		this.simpleJavaType = simpleJavaType;
	}
	/**
	 * notBaseField.
	 *
	 * @return the notBaseField
	 */
	public boolean isNotBaseField() {
		return notBaseField;
	}
	/**
	 * notBaseField.
	 *
	 * @param notBaseField the notBaseField to set
	 */
	public void setNotBaseField(boolean notBaseField) {
		this.notBaseField = notBaseField;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	
}
