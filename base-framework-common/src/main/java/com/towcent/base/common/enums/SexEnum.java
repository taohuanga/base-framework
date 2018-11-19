package com.towcent.base.common.enums;

/**
 * 性别枚举
 * 
 */
public enum SexEnum {
	/**
	 * 采购订单编号
	 */
	MAN("男"),

	WOMEN("女");

	private String name;

	SexEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
