package com.towcent.base.common.utils;

/**
 * TODO: 增加描述
 * 
 */
public class DataSourceSwitch {
	/** 当前数据源 */
	private static final ThreadLocal<String> _currentDataSource = new ThreadLocal<String>();

	/**
	 * 设置当前数据源。
	 * 
	 * @param dataSourceEnum
	 */
	public static void setCurrentDataSource(String dataSourceName) {
		_currentDataSource.set(dataSourceName);
	}

	public static String getCurrentdatasource() {
		// logger.debug("当前数据源：" + _currentDataSource.get());
		return _currentDataSource.get();
	}
}
