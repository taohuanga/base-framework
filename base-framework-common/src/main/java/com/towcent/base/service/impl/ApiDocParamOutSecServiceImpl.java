package com.towcent.base.service.impl;

import com.towcent.base.dal.db.ApiDocParamOutSecMapper;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.service.ApiDocParamOutSecService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("apiDocParamOutSecServiceImpl")
public class ApiDocParamOutSecServiceImpl extends BaseCrudServiceImpl implements ApiDocParamOutSecService {

    @Resource
    private ApiDocParamOutSecMapper apiDocParamOutSecMapper;

    @Override
    public CrudMapper init() {
        return apiDocParamOutSecMapper;
    }

}