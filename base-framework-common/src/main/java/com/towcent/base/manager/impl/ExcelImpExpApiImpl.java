/**
 * 
 */
package com.towcent.base.manager.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.towcent.base.common.excel.AbstractExportExcel;
import com.towcent.base.common.excel.AbstractImportExcel;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.manager.ExcelImpExpApi;

/**
 * @author shiwei
 *
 */
@Service
public class ExcelImpExpApiImpl implements ExcelImpExpApi {

	
	@Override
	public String expExcel(Map<String, Object> params, String serviceId) {
		AbstractExportExcel<?> exp = (AbstractExportExcel<?>)SpringContextHolder.getBean(serviceId);
		return exp.getTemplate(params);
	}

	@Override
	public void impExcel(String exlData, String serviceId, Map<String, Object> params) {
		AbstractImportExcel<?> imp = (AbstractImportExcel<?>)SpringContextHolder.getBean(serviceId);
		imp.intoDb(exlData, params);
	}

}
