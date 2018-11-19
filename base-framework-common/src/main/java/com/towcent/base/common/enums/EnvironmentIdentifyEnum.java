package com.towcent.base.common.enums;

/**
 * 环境标识枚举
 */
public enum EnvironmentIdentifyEnum {
	/**
	 * 开发环境
	 */
	DEV("dev"),
	/**
	 * 测试环境
	 */
	TEST("test"),
	/**
	 * 体验环境
	 */
	EXPERIENCE("experience"),
	/**
	 * 生产环境
	 */
	ONLINE("online"),
	/**
	 * 培训环境
	 */
	TRAIN("train");

	private String identify;

	EnvironmentIdentifyEnum(String identify) {
		this.identify = identify;
	}

	public String getIdentify() {
		return identify;
	}
}
