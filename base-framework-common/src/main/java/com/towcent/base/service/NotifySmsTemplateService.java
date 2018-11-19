package com.towcent.base.service;

import com.towcent.base.common.exception.ServiceException;

/**
 * notify_sms_template数据库操作接口
 * 
 * @author Generator
 * @date 2016-04-07 20:11:43
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface NotifySmsTemplateService extends BaseCrudService {
	
	void initSmsTemplate() throws ServiceException;
	
}