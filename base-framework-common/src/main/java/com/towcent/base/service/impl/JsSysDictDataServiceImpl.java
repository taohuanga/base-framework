package com.towcent.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.JsSysDictDataMapper;
import com.towcent.base.service.JsSysDictDataService;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 09:41:10
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("jsSysDictDataServiceImpl")
public class JsSysDictDataServiceImpl extends BaseCrudServiceImpl implements JsSysDictDataService {

    @Resource
    private JsSysDictDataMapper jsSysDictDataMapper;

    @Override
    public CrudMapper init() {
        return jsSysDictDataMapper;
    }

}