package com.towcent.base.common.model;

/**
 * 编码规则创建者
 * 实现该接口可以实现比较自由的编码规则：默认是“前缀+格式化时间串+序列号”的格式，可以自己在buildNo组合其他属性到编码中
 * 举例：buildNo方法中可以自己加入其他的属性，如门店编号等
 */
public interface CodingRuleBuilder {
	
	public void setMerchantId(Integer merchantId);
	
	public Integer getMerchantId();
	
	/**
	 * 设置requestId：即一种单据的唯一标识
	 * @param requestId
	 * @return
	 */
	public void setRequestId(String requestId);
	
	public String getRequestId();
	
	/**
	 * 设置前缀
	 */
	public void setPrefix(String prefix);
	
	/**
	 * 设置格式化的时间串
	 */
	public void setFormatedTime(String formatedTime);
	
	/**
	 * 设置序列号
	 */
	public void setSequence(String sequence);
	
	/**
	 * 构造编号，该方法决定了返回编码的格式
	 * @return
	 */
	public String buildNo();
}
