package com.towcent.base.common.model;

/**
 * 
 * CodingRuleBuilder接口的默认实现，如果要实现自定义编码规则，请重写buildNo()方法
 */
public class NoTimeCodingRuleBuilder implements CodingRuleBuilder {
	
	private Integer merchantId;
	
	private String requestId;
	
	private String prefix;
	
	@SuppressWarnings("unused")
	private String formatedTime;
	
	private String sequence;
	
	@Override
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	
	@Override
	public Integer getMerchantId() {
		return this.merchantId;
	}
	
	@Override
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Override
	public String getRequestId() {
		return this.requestId;
	}
	
	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void setFormatedTime(String formatedTime) {
		this.formatedTime = formatedTime;
	}
	
	@Override
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public String buildNo() {
		return prefix + sequence;
	}	
}
