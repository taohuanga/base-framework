package com.towcent.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.ApiDocMain;
import com.towcent.base.common.model.ApiDocParamIn;
import com.towcent.base.common.model.ApiDocPublicParam;
import com.towcent.base.dal.db.ApiDocMainMapper;
import com.towcent.base.dal.db.ApiDocParamInMapper;
import com.towcent.base.dal.db.ApiDocParamOutMapper;
import com.towcent.base.dal.db.ApiDocParamOutSecMapper;
import com.towcent.base.dal.db.ApiDocPublicParamMapper;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.service.ApiDocMainService;

/**
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("apiDocMainServiceImpl")
public class ApiDocMainServiceImpl extends BaseCrudServiceImpl implements ApiDocMainService {

    @Resource
    private ApiDocMainMapper apiDocMainMapper;
    @Resource
    private ApiDocPublicParamMapper publicParamMapper;
    @Resource
    private ApiDocParamInMapper paramInMapper;
    @Resource
    private ApiDocParamOutMapper paramOutMapper;
    @Resource
    private ApiDocParamOutSecMapper paramOutSecMapper;
    
    @Override
    public CrudMapper init() {
        return apiDocMainMapper;
    }
    
    @Override
    public ApiDocMain getApiDocByParam(Map<String, Object> params) throws ServiceException {
    	try {
			List<ApiDocMain> list = this.findByBiz(params);
			if (CollectionUtils.isEmpty(list)) {
			    return null;
            }
			
			ApiDocMain main = list.get(0);
			// 查询公共参数
			params.clear();
			List<ApiDocPublicParam> publicParams = publicParamMapper.selectByParams(null);
			main.setPublicParams(publicParams);
			
			// 查询入参列表
			params.put("interfaceId", main.getId());
			List<ApiDocParamIn> paramIns = paramInMapper.selectByParams(params);
			
			return null;
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException("", e);
		}
    		
    }
}