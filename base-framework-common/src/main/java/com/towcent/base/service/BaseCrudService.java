package com.towcent.base.service;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.page.SimplePage;

import java.util.List;
import java.util.Map;

/**
 * crud对应Service类
 * 
 * @author HuangTao
 * @version 2015年5月25日
 */
public interface BaseCrudService {
	
	public <T> int deleteById(Object id) throws ServiceException;
	
	public <T> int add(T modelType) throws ServiceException;
	
	public <T> int addSelective(T modelType) throws ServiceException;
	
	public <T> T findById(Object id) throws ServiceException;
	
	/**
	 * 根据参数查询单个对象
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public <T> T findOne(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据参数查询
	 * 
	 * @param params    页面其他参数
	 * @return
	 * @throws ServiceException
	 */
	public <T> List<T> findByBiz(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据参数的Key-val查询
	 * @param key 数据库对应的字段名
	 * @param val
	 * @return
	 * @throws ServiceException
	 */
	public <T> List<T> findByKeyVal(String key, Object val) throws ServiceException;
	
	/**
	 * 根据参数的Key-val获取单个结果集
	 * @param key 数据库对应的字段名
	 * @param val
	 * @return
	 * @throws ServiceException
	 */
	public <T> T findByKeyValSingle(String key, Object val) throws ServiceException;
	
	/**
	 * 根据id修改实体
	 * 
	 * @param modelType
	 * @return
	 * @throws ServiceException
	 */
	public <T> int modifyById(T modelType) throws ServiceException;
	
	/**
	 * 根据id动态修改实体
	 * 
	 * @param modelType
	 * @return
	 * @throws ServiceException
	 */
	public <T> int modifyByIdSelective(T modelType) throws ServiceException;

	/**
	 * 根据参数查询总记录数
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int findCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 根据参数查询列表
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public <T> List<T> findByPage(SimplePage page) throws ServiceException;

}
