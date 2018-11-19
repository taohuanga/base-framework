package com.towcent.base.service;

import java.util.List;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysArea;

/**
 * js_sys_area 数据库操作Service接口
 * 
 * @author huangtao
 * @date 2018-10-31 15:58:33
 * @version 1.0
 * @copyright facegarden.com
 */
public interface JsSysAreaService extends BaseCrudService {
	String getAreaDesc(String provinceCode, String cityCode, String areaCode, String spliter) throws ServiceException;

	String getAreaNameById(String code) throws ServiceException;
	
	/**
	 * @Title: getAreaById
	 * @Description: 通过Id获取地址对象.
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return: SysArea
	 */
	JsSysArea getAreaById(String code) throws ServiceException;
	
	/**
	 * @Title: getParentAreasById
	 * @Description: 通过id获取所有父级对象集合.
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return: List<SysArea>
	 */
	List<JsSysArea> getParentAreasById(String code) throws ServiceException;
	
	/**
	 * 通过地址Id获取地址的全描述【广东省-深圳市-福田区】.
	 * @Title getAreaDescById
	 * @param id
	 * @param spilter 
	 * @return
	 * @throws ServiceException
	 */
	String getAreaDescById(String code, String spilter) throws ServiceException;
	
	/**
	 * @Title: getParentIdsById
	 * @Description: 通过id获取所有父级地址id数组.
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return: Integer[]
	 */
	String[] getParentIdsById(String code) throws ServiceException; 
}