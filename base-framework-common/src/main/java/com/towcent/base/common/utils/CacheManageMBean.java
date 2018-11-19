package com.towcent.base.common.utils;

/**
 * 让Cache支持JMX
 * @author wei.b
 * @date 2014-1-13 下午6:51:05
 * @version 0.1.0 
 */
public interface CacheManageMBean {
	/**
	 * 对外提供的接口
	 */
	public void clearAll();
}
