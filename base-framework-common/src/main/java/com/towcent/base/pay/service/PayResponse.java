package com.towcent.base.pay.service;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayMessageRouter;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.MsgType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.towcent.base.pay.entity.PayAccount;
import com.towcent.base.pay.entity.PayType;
import com.towcent.base.pay.service.handler.AliPayMessageHandler;
import com.towcent.base.pay.service.handler.WxPayMessageHandler;
import com.towcent.base.pay.service.interceptor.AliPayMessageInterceptor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 支付响应对象
 */
public class PayResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
    private AutowireCapableBeanFactory spring;

    private PayConfigStorage storage;

    private PayService service;

    private PayMessageRouter router;

    public PayResponse() {

    }

    /**
     * 初始化支付配置
     * @param apyAccount 账户信息
     * @see ApyAccount 对应表结构详情--》 /pay-java-demo/resources/apy_account.sql
     */
    public void init(PayAccount payAccount) {
        //根据不同的账户类型 初始化支付配置
        this.service = payAccount.getPayType().getPayService(payAccount);
        this.storage = service.getPayConfigStorage();
        //这里设置代理配置
        // service.setRequestTemplateConfigStorage(getHttpConfigStorage());
        buildRouter(payAccount.getPayId());
    }

    /**
     * 获取http配置，如果配置为null则为默认配置，无代理。
     * 此处非必需
     * @return
     */
    public HttpConfigStorage getHttpConfigStorage(){
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        //http代理地址
        httpConfigStorage.setHttpProxyHost("192.168.1.69");
        //代理端口
        httpConfigStorage.setHttpProxyPort(3308);
        //代理用户名
        httpConfigStorage.setHttpProxyUsername("user");
        //代理密码
        httpConfigStorage.setHttpProxyPassword("password");
        return httpConfigStorage;
    }


    /**
     * 配置路由
     * @param payId 指定账户id，用户多微信支付多支付宝支付
     */
    private void buildRouter(Integer payId) {
        router = new PayMessageRouter(this.service);
        router
                .rule()
                .async(false)
                .msgType(MsgType.text.name()) //消息类型
                .payType(PayType.aliPay.name()) //支付账户事件类型
                .interceptor(new AliPayMessageInterceptor()) //拦截器
                .handler(autowire(new AliPayMessageHandler(payId))) //处理器
                .end()
                .rule()
                .async(false)
                .msgType(MsgType.xml.name())
                .payType(PayType.wxPay.name())
                .handler(autowire(new WxPayMessageHandler(payId)))
                .end()

        ;
    }


    private PayMessageHandler autowire(PayMessageHandler handler) {
        spring.autowireBean(handler);
        return handler;
    }

    public PayConfigStorage getStorage() {
        return storage;
    }

    public PayService getService() {
        return service;
    }

    public PayMessageRouter getRouter() {
        return router;
    }
}
