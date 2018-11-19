package com.towcent.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysLogisticsCompanyMapper;
import com.towcent.base.service.SysLogisticsCompanyService;

/**
 * 
 * @author huangtao
 * @date 2018-07-11 18:40:57
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("sysLogisticsCompanyServiceImpl")
public class SysLogisticsCompanyServiceImpl extends BaseCrudServiceImpl implements SysLogisticsCompanyService {

    @Resource
    private SysLogisticsCompanyMapper sysLogisticsCompanyMapper;

    @Override
    public CrudMapper init() {
        return sysLogisticsCompanyMapper;
    }

}