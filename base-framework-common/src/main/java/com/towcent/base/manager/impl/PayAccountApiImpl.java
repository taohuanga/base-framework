package com.towcent.base.manager.impl;

import com.towcent.base.manager.PayAccountApi;
import com.towcent.base.pay.service.PayResponse;
import com.towcent.base.service.PayAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangtao on 2017/6/29.
 */
@Service
public class PayAccountApiImpl implements PayAccountApi {

    @Autowired
    private PayAccountService payAccountService;

    @Override
    public PayResponse getPayResponse(Integer id) {
        return payAccountService.getPayResponse(id);
    }
}
