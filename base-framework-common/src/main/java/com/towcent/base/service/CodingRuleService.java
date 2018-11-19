package com.towcent.base.service;

import com.towcent.base.common.enums.RuleTypeEnum;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.CodingRuleBuilder;
import com.towcent.base.common.model.CodingRuleBuilderExt;

/**
 * 编码规则接口类
 * 
 */
public interface CodingRuleService {
	
	/**
	 * 获取编号:
	 * 编码格式为：前綴 + 日期串 + 序列号
	 * @param type
	 * @param merchantId
	 * @return
	 * @throws ServiceException
	 */
	public String getSerialNo(RuleTypeEnum type, Integer merchantId) throws ServiceException;
	
	/**
	 * 获取编号:
	 * 编码格式为：前綴 + 日期串 + 序列号
	 * @param requestId
	 * @param merchantId
	 * @return
	 * @throws ServiceException
	 */
	public String getSerialNo(String requestId, Integer merchantId) throws ServiceException;
	
	/**
	 * 回收编号：如果业务新增单据时保存单据失败，可以调用该接口回收已经获取到的编号，如果不回收，该编号就丢弃，会出现编号不连续
	 * 
	 * @param requestId
	 * @param serialNo
	 * @param merchantId
	 */
	public void recycleSerialNo(String requestId, String serialNo, Integer merchantId);
	
	/**
	 * 获取编号
	 * 编码格式：自定义，在CodingRuleBuilder接口中buildNo()方法自己定义编码的组成格式
	 *          不局限于（前綴 + 日期串 + 序列号）的格式，甚至可以自己加入比如门店编号等其他属性
	 * @param builder
	 * @return
	 * @throws ServiceException
	 */
	public String getSerialNo(CodingRuleBuilder builder) throws ServiceException;
	
	/**
	 * 获取编号
	 * 编码格式：自定义，在CodingRuleBuilderExt接口中buildNo()方法自己定义编码的组成格式
	 *          不局限于（前綴 + 日期串 + 序列号）的格式，甚至可以自己加入比如门店编号等其他属性
	 * @param builder
	 * @param subId
	 * @return
	 * @throws ServiceException
	 */
	public String getSerialNo(CodingRuleBuilderExt builderExt) throws ServiceException;
}