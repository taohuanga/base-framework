package com.towcent.base.common.enums;

/**
 * 调度状态枚举
 */
public enum JobBizStatusEnum {
	/**
	 * 正在初始化
	 */
	INITIALIZING,
	/**
	 * 初始化完成
	 */
	INITIALIZED,
	/**
	 * 运行中
	 */
	RUNNING,
	/**
	 * 异常中断
	 */
	INTERRUPTED,
	/**
	 * 已停止
	 */
	STOPED,
	/**
	 * 已暂停
	 */
	PAUSED,
	/**
	 * 等待
	 */
	WAITING,
	/**
	 * 处理完成
	 */
	FINISHED;
}