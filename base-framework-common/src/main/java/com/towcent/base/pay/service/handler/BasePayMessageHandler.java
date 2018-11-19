package com.towcent.base.pay.service.handler;

import com.egzosn.pay.common.api.PayMessageHandler;

/**
 *
 */
public abstract class BasePayMessageHandler implements PayMessageHandler {
    //支付账户id
    private Integer payId;

    public BasePayMessageHandler(Integer payId) {
        this.payId = payId;
    }

    public Integer getPayId() {
        return payId;
    }
}
