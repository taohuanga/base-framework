package com.towcent.base.manager;

import com.towcent.base.common.enums.CommonOperatorEnum;
import com.towcent.base.common.exception.ManagerException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.page.SimplePage;

import java.util.List;
import java.util.Map;

public interface BaseCrudManager{
	
	/**
	 * 
	 * @param modelType
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> int deleteById(ModelType modelType) throws ManagerException;
	
	/**
	 * 
	 * @param modelType
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> int add(ModelType modelType) throws ManagerException;
	
	/**
	 * 
	 * @param modelType
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> int modifyById(ModelType modelType)throws ManagerException;
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int findCount(Map<String,Object> params)throws ManagerException;
	/**
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> List<ModelType> findByPage(SimplePage page)throws ManagerException;
	
	/**
	 * 
	 * @param modelType
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> ModelType findById(Object id)throws ManagerException;
	
	/**
	 * 
	 * @param modelType
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> ModelType findOne(Map<String,Object> params)throws ManagerException;
	
	/**
	 * 根据业务参数查询列表
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public <ModelType> List<ModelType> findByBiz(Map<String,Object> params)throws ManagerException;
	
	
	/**
	 * 公共的保存操作
	 * @param params key:增、删、改操作枚举 value:对象列表
	 * @return 影响条数
	 * @throws ServiceException 
	 */
	public <ModelType> int save(Map<CommonOperatorEnum,List<ModelType>> params) throws ManagerException;

}
