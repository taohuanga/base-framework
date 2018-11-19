package com.towcent.base.service.impl;

import com.towcent.base.dal.db.ApiDocParamOutMapper;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.service.ApiDocParamOutService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("apiDocParamOutServiceImpl")
public class ApiDocParamOutServiceImpl extends BaseCrudServiceImpl implements ApiDocParamOutService {

    @Resource
    private ApiDocParamOutMapper apiDocParamOutMapper;

    @Override
    public CrudMapper init() {
        return apiDocParamOutMapper;
    }

}