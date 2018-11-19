package com.towcent.base.common.httpapi;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.httpapi.example.service.PriceGetBalanceRequest;
import com.towcent.base.common.httpapi.example.vo.request.PriceGetBalanceReq;
import com.towcent.base.common.httpapi.example.vo.response.PriceGetBalanceResp;
import com.towcent.base.common.model.JsSysDictData;
import com.towcent.base.manager.BaseCommonApi;

public class JDVopTest extends BaseTest {
	
	@Resource PriceGetBalanceRequest balanceRequest;
	@Resource
	BaseCommonApi api;
	
	@Test
	public void getBalance() throws Exception {		
		PriceGetBalanceReq priceGetBalanceReq = new PriceGetBalanceReq();
		PriceGetBalanceResp priceGetBalanceResp = new PriceGetBalanceResp();
		
		priceGetBalanceReq.setPayType(4);
		priceGetBalanceResp = (PriceGetBalanceResp) balanceRequest.invoke(priceGetBalanceReq, priceGetBalanceResp);
		
		System.out.println(priceGetBalanceResp.getResult());
	}
	
	@Test
	public void testDict() throws Exception {		
		List<JsSysDictData> list=api.getDictListByKey(0, "sys_device_type");
		Map<String, JsSysDictData> map = api.getDictMapByKey(0, "sys_device_type");
		JsSysDictData data2=api.getDictByKeyVal(0, "sys_device_type", "mobileApp");
		JsSysDictData data1=api.getDictByKeyName(0, "sys_device_type", "手机APP");
		System.out.println("111");
	}
	
}
