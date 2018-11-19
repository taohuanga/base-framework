package com.towcent.base.common.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSetter;
/**
 * 
 * TODO: 增加描述 下拉框公用类
 * 
 * @date 2013-9-22 下午1:20:46
 * @version 0.1.0 
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.NONE)
public class Column{
	
		private String name;
		private String type;
		
		
		// 做下拉框列表加载
		private String valueField; // 值
		private String textField;// 显示文本
		
		//动态查询条件
		private String columnRelation; //关系  and or
		private String columnName;//列名
		private String columnCondition; // 条件
		private String columnValue; // 数值  
		
		private String queryConditionSQL; //公用查询条件是拼装的SQL
		private String errorMsg;//返回验证SQL不合法时的错误信息
		
		private String preColNames;
		private String preColValus;
		
		private  String moduleFlag;
		
		public String getModuleFlag() {
			return moduleFlag;
		}

		public void setModuleFlag(String moduleFlag) {
			this.moduleFlag = moduleFlag;
		}

		public Column() {
		}
		
		public Column(String name, String type) {
			super();
			this.name = name;
			this.type = type;
		}
		public String getName() {
			return name;
		}
		@JsonSetter(value="Name")
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		@JsonSetter(value="Type")
		public void setType(String type) {
			this.type = type;
		}

		public String getValueField() {
			return valueField;
		}

		public void setValueField(String valueField) {
			this.valueField = valueField;
		}

		public String getTextField() {
			return textField;
		}

		public void setTextField(String textField) {
			this.textField = textField;
		}

		public String getColumnRelation() {
			return columnRelation;
		}

		public void setColumnRelation(String columnRelation) {
			this.columnRelation = columnRelation;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getColumnCondition() {
			return columnCondition;
		}

		public void setColumnCondition(String columnCondition) {
			this.columnCondition = columnCondition;
		}

		public String getColumnValue() {
			return columnValue;
		}

		public void setColumnValue(String columnValue) {
			this.columnValue = columnValue;
		}

		public String getQueryConditionSQL() {
			return queryConditionSQL;
		}

		public void setQueryConditionSQL(String queryConditionSQL) {
			this.queryConditionSQL = queryConditionSQL;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public String getPreColNames() {
			return preColNames;
		}

		public void setPreColNames(String preColNames) {
			this.preColNames = preColNames;
		}

		public String getPreColValus() {
			return preColValus;
		}

		public void setPreColValus(String preColValus) {
			this.preColValus = preColValus;
		}
		
		
		
		
		
	}