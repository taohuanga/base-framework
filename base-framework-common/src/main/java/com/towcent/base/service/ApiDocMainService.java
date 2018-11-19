package com.towcent.base.service;

import java.util.Map;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.ApiDocMain;

/**
 * api_doc_main 数据库操作Service接口
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
public interface ApiDocMainService extends BaseCrudService {
	
	ApiDocMain getApiDocByParam(Map<String, Object> params) throws ServiceException;
	
}