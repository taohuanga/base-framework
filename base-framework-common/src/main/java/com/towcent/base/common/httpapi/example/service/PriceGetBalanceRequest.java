package com.towcent.base.common.httpapi.example.service;

import org.springframework.stereotype.Service;

import com.towcent.base.common.httpapi.example.common.JDVopHttpRequest;

@Service
public class PriceGetBalanceRequest extends JDVopHttpRequest {

	@Override
	protected String getHttpMethodName() {
		return "api/price/getBalance";
	}
	
}
