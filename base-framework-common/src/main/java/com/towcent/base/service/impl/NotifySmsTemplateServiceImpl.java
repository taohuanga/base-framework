package com.towcent.base.service.impl;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.NotifySmsTemplate;
import com.towcent.base.common.utils.SMSUtils;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.NotifySmsTemplateMapper;
import com.towcent.base.service.NotifySmsTemplateService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Generator
 * @date 2016-04-07 20:11:43
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("notifySmsTemplateService")
public class NotifySmsTemplateServiceImpl extends BaseCrudServiceImpl implements NotifySmsTemplateService, InitializingBean {

    @Resource
    private NotifySmsTemplateMapper notifySmsTemplateMapper;

    @Override
    public CrudMapper init() {
        return notifySmsTemplateMapper;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    	initSmsTemplate();
    }
    
    @Override
    public void initSmsTemplate() throws ServiceException {
        Map<String,Object> map = new HashMap<String,Object>();
    	List<NotifySmsTemplate> list = notifySmsTemplateMapper.selectByParams(map);
    	if (!CollectionUtils.isEmpty(list)) {
    		for (NotifySmsTemplate template : list) {
				SMSUtils.MAP.put(template.getSmsType(), template.getContent());
			}
    	}
    }
    
}