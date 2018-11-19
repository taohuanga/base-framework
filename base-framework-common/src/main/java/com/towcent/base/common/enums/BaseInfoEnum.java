package com.towcent.base.common.enums;

/**
 * 性别枚举
 * 
 */
public enum BaseInfoEnum {
	/**
	 * 采购订单编号
	 */
	NAME("姓名"),

	SEX("性别"),

	ADDRESS("住址"),

	IDCARD("公民身份号码"),

	BIRTHDAY("出生");

	private String name;

	BaseInfoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
