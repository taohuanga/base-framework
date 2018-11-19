package com.towcent.base.service.impl;

import com.towcent.base.dal.db.ApiDocPublicParamMapper;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.service.ApiDocPublicParamService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("apiDocPublicParamServiceImpl")
public class ApiDocPublicParamServiceImpl extends BaseCrudServiceImpl implements ApiDocPublicParamService {

    @Resource
    private ApiDocPublicParamMapper apiDocPublicParamMapper;

    @Override
    public CrudMapper init() {
        return apiDocPublicParamMapper;
    }

}