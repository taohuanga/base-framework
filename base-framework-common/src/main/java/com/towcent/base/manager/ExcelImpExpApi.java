/**
 * 
 */
package com.towcent.base.manager;

import java.util.Map;

/**
 * Excel导入导出接口
 * @author shiwei
 *
 */
public interface ExcelImpExpApi {

	/**
	 * 导出
	 * @param params 查询参数
	 * @param serviceId 实现导出的spring对象id
	 * @return
	 */
	public String expExcel(Map<String, Object> params, String serviceId);
	
	
	/**
	 * 导入
	 * @param exlData Base64编码的exl数据
	 * @param serviceId  实现导入的spring对象id
	 * @param params 导入参数（过滤数据）
	 */
	public void impExcel(String exlData, String serviceId, Map<String, Object> params);
}
