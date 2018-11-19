package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.NotifyMailMapper;
import com.towcent.base.service.NotifyMailService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-03-24 10:53:20
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("notifyMailServiceImpl")
public class NotifyMailServiceImpl extends BaseCrudServiceImpl implements NotifyMailService {

    @Resource
    private NotifyMailMapper notifyMailMapper;

    @Override
    public CrudMapper init() {
        return notifyMailMapper;
    }

}