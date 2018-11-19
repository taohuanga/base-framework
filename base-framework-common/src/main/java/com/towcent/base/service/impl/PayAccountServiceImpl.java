package com.towcent.base.service.impl;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.PayAccountMapper;
import com.towcent.base.pay.entity.PayAccount;
import com.towcent.base.pay.service.PayResponse;
import com.towcent.base.service.PayAccountService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Generator
 * @date 2017-06-22 15:10:19
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("payAccountService")
public class PayAccountServiceImpl extends BaseCrudServiceImpl implements PayAccountService {

	// 缓存
    private final static Map<Integer, PayResponse> payResponses = new HashMap<Integer, PayResponse>();
    
    @Resource
    private AutowireCapableBeanFactory spring;
    
    @Resource
    private PayAccountMapper payAccountMapper;

    @Override
    public CrudMapper init() {
        return payAccountMapper;
    }
    
    /**
     *  获取支付响应
     * @param id 账户id
     * @return
     * @throws ServiceException 
     */
    @Override
    public PayResponse getPayResponse(Integer id) {
    	PayResponse payResponse = payResponses.get(id);
        try {
			if (payResponse  == null) {
			    PayAccount payAccount = this.findByKeyValSingle("payId", id);
			    if (payAccount == null) {
			        throw new IllegalArgumentException ("无法查询");
			    }
			    payResponse = new PayResponse();
			    spring.autowireBean(payResponse);
			    payResponse.init(payAccount);
			    payResponses.put(id, payResponse);
			    // 查询
			}
		} catch (BeansException e) {
			logger.error("", e);
		} catch (ServiceException e) {
			logger.error("", e);
		}
        return payResponse;
    }
}