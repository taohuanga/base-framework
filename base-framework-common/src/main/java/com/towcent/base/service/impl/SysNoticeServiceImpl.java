package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysNoticeMapper;
import com.towcent.base.service.SysNoticeService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-03-24 10:51:53
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("sysNoticeServiceImpl")
public class SysNoticeServiceImpl extends BaseCrudServiceImpl implements SysNoticeService {

    @Resource
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public CrudMapper init() {
        return sysNoticeMapper;
    }

}