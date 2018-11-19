package com.towcent.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysCarouselConfMapper;
import com.towcent.base.service.SysCarouselConfService;

/**
 * 
 * @author licheng
 * @date 2018-04-18 11:11:48
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("sysCarouselConfServiceImpl")
public class SysCarouselConfServiceImpl extends BaseCrudServiceImpl implements SysCarouselConfService {

    @Resource
    private SysCarouselConfMapper sysCarouselConfMapper;

    @Override
    public CrudMapper init() {
        return sysCarouselConfMapper;
    }

}