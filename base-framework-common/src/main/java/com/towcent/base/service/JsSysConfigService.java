package com.towcent.base.service;

import java.math.BigDecimal;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysConfig;

/**
 * js_sys_config 数据库操作Service接口
 * 
 * @author huangtao
 * @date 2018-10-31 11:31:24
 * @version 1.0
 * @copyright facegarden.com
 */
public interface JsSysConfigService extends BaseCrudService {
	/**
	 * 根据数据Key获取属性对象.
	 * @Title getSysPropertyByKey
	 * @param key
	 * @return
	 * @throws ServiceException
	 */
	JsSysConfig getSysPropertyByKey(Integer merchantId, String key) throws ServiceException;
	
	/**
	 * 获取属性值 -- 返回字符串.
	 * @Title getSysPropertyToString
	 * @param key
	 * @return
	 * @throws ServiceException
	 */
	String getSysPropertyToString(Integer merchantId, String key) throws ServiceException;
	
	/**
	 * 获取属性值 -- 返回数字.
	 * @Title getSysPropertyToInt
	 * @param key
	 * @return
	 * @throws ServiceException
	 */
	Integer getSysPropertyToInt(Integer merchantId, String key) throws ServiceException;
	
	/**
	 * 获取属性值 -- 返回布尔类型.
	 * @Title getSysPropertyToBoolean
	 * @param key
	 * @return
	 * @throws ServiceException
	 */
	Boolean getSysPropertyToBoolean(Integer merchantId, String key) throws ServiceException;
	
	/**
	 * 获取属性值 -- 返回double.
	 * @Title getSysPropertyToDouble
	 * @param key
	 * @return
	 * @throws ServiceException
	 */
	Double getSysPropertyToDouble(Integer merchantId, String key) throws ServiceException;
	
	/**
	 * 获取属性值 -- 返回浮点型.
	 * @Title getSysPropertyToBigDecimal
	 * @param key
	 * @return
	 * @throws ServiceException
	 */
	BigDecimal getSysPropertyToBigDecimal(Integer merchantId, String key) throws ServiceException;
}