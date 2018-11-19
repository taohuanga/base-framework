/**
 * 
 */
package com.towcent.base.common.excel.example.exp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.towcent.base.common.excel.AbstractExportExcel;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.ApiDocMain;
import com.towcent.base.service.ApiDocMainService;

/**
 * @author shiwei
 *
 */
@Service
public class ApiDocMainExportExcel extends AbstractExportExcel<ApiDocMain> {

	@Resource
	private ApiDocMainService apiDocMainService;
	
	@Override
	protected String[] getHeader() {
		return  new String[]{"接口组", "接口编码"};
	}

	@Override
	protected String getTitle() {
		return "接口导入模板";
	}

	@Override
	protected List<List<String>> getDataList(Map<String, Object> map) {
		// 数据集合
		List<List<String>> dataList = Lists.newArrayList();
		try {
			List<ApiDocMain> list = apiDocMainService.findByBiz(null);
			List<String> data = null;
			for (ApiDocMain apiDocMain : list) {
				if(null != apiDocMain){
					data = Lists.newArrayList();
					
					data.add(0, apiDocMain.getInterfaceGroup());
					data.add(1, apiDocMain.getInterfaceNo());
					
					dataList.add(data);
				}
			}
		} catch (ServiceException e) {
			logger.error("获取导出模板出错", e.fillInStackTrace());
		}
		return dataList;
	}

}
