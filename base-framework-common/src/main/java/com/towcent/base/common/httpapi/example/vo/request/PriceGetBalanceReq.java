package com.towcent.base.common.httpapi.example.vo.request;

import com.towcent.base.common.httpapi.example.common.BaseRequestBody;

/**
 * 统一余额查询接口
 * @author huangtao
 *
 */
public class PriceGetBalanceReq extends BaseRequestBody {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 支付类型  4：余额 7：网银钱包 101：金采支付
	 */
	private Integer payType;

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
}
