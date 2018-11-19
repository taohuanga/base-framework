package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysAboutMapper;
import com.towcent.base.service.SysAboutService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author licheng
 * @date 2018-02-01 16:48:45
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("sysAboutServiceImpl")
public class SysAboutServiceImpl extends BaseCrudServiceImpl implements SysAboutService {

    @Resource
    private SysAboutMapper sysAboutMapper;

    @Override
    public CrudMapper init() {
        return sysAboutMapper;
    }

}