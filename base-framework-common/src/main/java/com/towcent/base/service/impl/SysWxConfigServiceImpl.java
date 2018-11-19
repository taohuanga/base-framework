package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysWxConfigMapper;
import com.towcent.base.service.SysWxConfigService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-06-26 18:31:02
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("sysWxConfigServiceImpl")
public class SysWxConfigServiceImpl extends BaseCrudServiceImpl implements SysWxConfigService {

    @Resource
    private SysWxConfigMapper sysWxConfigMapper;

    @Override
    public CrudMapper init() {
        return sysWxConfigMapper;
    }

}