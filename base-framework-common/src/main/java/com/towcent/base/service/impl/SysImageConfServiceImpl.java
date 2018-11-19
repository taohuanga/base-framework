package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysImageConfMapper;
import com.towcent.base.service.SysImageConfService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Generator
 * @date 2017-11-15 10:19:54
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysImageConfService")
public class SysImageConfServiceImpl extends BaseCrudServiceImpl implements SysImageConfService {

    @Resource
    private SysImageConfMapper sysImageConfMapper;

    @Override
    public CrudMapper init() {
        return sysImageConfMapper;
    }
}