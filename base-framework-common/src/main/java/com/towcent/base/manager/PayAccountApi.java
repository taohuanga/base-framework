package com.towcent.base.manager;

import com.towcent.base.pay.service.PayResponse;

/**
 * Created by huangtao on 2017/6/29.
 */
public interface PayAccountApi {

    /**
     *  获取支付响应
     * @param id 账户id
     * @return
     */
    PayResponse getPayResponse(Integer id);

}
