package com.towcent.base.common.enums;

/**
 * 模块验证枚举
 * 
 */
public enum OperationVerifyEnum {
	NONE(1), ADD(2), MODIFY(4), REMOVE(8), EXPORT(16), VERIFY(32), IGNORE(64);

	public final int bitNum;

	OperationVerifyEnum(int bitNum) {
		this.bitNum = bitNum;
	}
}
