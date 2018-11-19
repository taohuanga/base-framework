package com.towcent.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.JsSysDictTypeMapper;
import com.towcent.base.service.JsSysDictTypeService;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 09:41:48
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("jsSysDictTypeServiceImpl")
public class JsSysDictTypeServiceImpl extends BaseCrudServiceImpl implements JsSysDictTypeService {

    @Resource
    private JsSysDictTypeMapper jsSysDictTypeMapper;

    @Override
    public CrudMapper init() {
        return jsSysDictTypeMapper;
    }

}