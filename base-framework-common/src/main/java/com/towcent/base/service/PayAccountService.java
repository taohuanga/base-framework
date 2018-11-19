package com.towcent.base.service;

import com.towcent.base.pay.service.PayResponse;

/**
 * pay_account数据库操作接口
 * 
 * @author Generator
 * @date 2017-06-22 15:10:19
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface PayAccountService extends BaseCrudService {

    /**
     *  获取支付响应
     * @param id 账户id
     * @return
     */
    PayResponse getPayResponse(Integer id);
}