package com.towcent.base.common.model;

/**
 * 
 * CodingRuleBuilderExt接口的默认实现，如果要实现自定义编码规则，请重写buildNo()方法
 */
public class DefaultCodingRuleBuilderExt implements CodingRuleBuilderExt {
	
	private Integer merchantId;
	
	private String requestId;
	
	private String subRequestId;
	
	private String prefix;
	
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
		return prefix + formatedTime + sequence;
	}

	@Override
	public void setSubRequestId(String subRequestId) {
		this.subRequestId = subRequestId;
		
	}

	@Override
	public String getSubRequestId() {
		return subRequestId;
	}	
}
