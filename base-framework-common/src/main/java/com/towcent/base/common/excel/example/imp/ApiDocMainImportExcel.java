/**
 * 
 */
package com.towcent.base.common.excel.example.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.towcent.base.common.excel.AbstractImportExcel;
import com.towcent.base.common.model.ApiDocMain;
import com.towcent.base.service.ApiDocMainService;

/**
 * @author shiwei
 *
 */
@Service
public class ApiDocMainImportExcel extends AbstractImportExcel<ApiDocMain>{

	@Resource
	private ApiDocMainService apiDocMainService;
	
	@Override
	protected Map<String, String> getHeader() {
        Map<String, String> header = Maps.newHashMap();
		
		header.put("接口组", "interfaceGroup");
		header.put("接口编码", "interfaceNo");
		
		return header;
	}

	@Override
	public void intoDb(String base64Data, Map<String, Object> map) {
		List<ApiDocMain> list = this.getData(this.getFileName(base64Data), ApiDocMain.class);
		for (ApiDocMain apiDocMain : list) {
			if(null != apiDocMain){
				try {
					apiDocMainService.add(apiDocMain);
				} catch (Exception e) {
					logger.error("数据入库失败", e.fillInStackTrace());
				}
			}
		}
	}

}
