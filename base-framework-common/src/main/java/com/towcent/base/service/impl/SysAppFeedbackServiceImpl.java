package com.towcent.base.service.impl;

import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.SysAppFeedbackMapper;
import com.towcent.base.service.SysAppFeedbackService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author huangtao
 * @date 2018-03-24 10:13:24
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("sysAppFeedbackServiceImpl")
public class SysAppFeedbackServiceImpl extends BaseCrudServiceImpl implements SysAppFeedbackService {

    @Resource
    private SysAppFeedbackMapper sysAppFeedbackMapper;

    @Override
    public CrudMapper init() {
        return sysAppFeedbackMapper;
    }

}