package com.towcent.base.common.model;

public interface CodingRuleBuilderExt extends CodingRuleBuilder {
	/**
	 * 设置subRequestId：可以是任意字符串，但一般设置为有一定含义的，如门店编号CA01BL，此时requestId为XXXX#CA01BL
	 * @param subRequestId
	 * @return
	 */
	public void setSubRequestId(String subRequestId);
	
	public String getSubRequestId();
}
