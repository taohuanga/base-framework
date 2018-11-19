package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysAppVersionMapper;
import com.towcent.base.service.SysAppVersionService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Generator
 * @date 2017-11-16 11:09:16
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysAppVersionService")
public class SysAppVersionServiceImpl extends BaseCrudServiceImpl implements SysAppVersionService {

    @Resource
    private SysAppVersionMapper sysAppVersionMapper;

    @Override
    public CrudMapper init() {
        return sysAppVersionMapper;
    }
}