package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.NotifySmsMapper;
import com.towcent.base.service.NotifySmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 * @author Generator
 * @date 2016-03-29 18:00:36
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("notifySmsService")
public class NotifySmsServiceImpl extends BaseCrudServiceImpl implements NotifySmsService {

    @Resource
    private NotifySmsMapper notifySmsMapper;

    @Override
    public CrudMapper init() {
        return notifySmsMapper;
    }
}