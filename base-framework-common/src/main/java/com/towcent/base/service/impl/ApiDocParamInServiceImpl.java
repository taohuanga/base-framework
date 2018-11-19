package com.towcent.base.service.impl;

import com.towcent.base.dal.db.ApiDocParamInMapper;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.service.ApiDocParamInService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("apiDocParamInServiceImpl")
public class ApiDocParamInServiceImpl extends BaseCrudServiceImpl implements ApiDocParamInService {

    @Resource
    private ApiDocParamInMapper apiDocParamInMapper;

    @Override
    public CrudMapper init() {
        return apiDocParamInMapper;
    }

}