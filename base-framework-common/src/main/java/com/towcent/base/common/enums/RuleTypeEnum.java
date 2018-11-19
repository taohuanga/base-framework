package com.towcent.base.common.enums;

/**
 * 编码规则枚举
 * 
 */
public enum RuleTypeEnum {
	
	/**
	 * 采购订单编号
	 */
	GOODS_NO("goods_no");
	
	private String name;

	RuleTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
