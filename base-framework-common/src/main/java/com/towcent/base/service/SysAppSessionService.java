package com.towcent.base.service;

import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.SysAppSession;

import java.util.List;

/**
 * sys_app_session数据库操作接口
 * 
 * @author Generator
 * @date 2015-11-19 15:54:07
 * @version 1.0.0
 * @copyright facegarden.com
 */
public interface SysAppSessionService extends BaseCrudService {
	
	/**
	 * <pre>
	 * 通过账号、设备号、账号类型获取有效的session记录
	 * </pre>
	 * @param merchantId 商户Id
	 * @param account  账号
	 * @param deviceNo 设备号
	 * @param appType  账号类型
	 * @return
	 * @throws ServiceException
	 */
	SysAppSession getAppSessionByParam(Integer merchantId, String account, String deviceNo, Integer appType) throws ServiceException;
	
	/**
	 * <pre>
	 * 暂时使用第三方推送服务商，则暂时不需要此方法<br>
	 * 
	 * (IOS推送Token)
	 * 通过账号、app类型 获取设备Token
	 * </pre>
	 * @param account 账号
	 * @param appType 账号类别
	 * @return
	 * @throws ServiceException
	 */
	List<String> getAppTokenListByParam(Integer merchantId, String account, Byte appType) throws ServiceException;
	
	/**
	 * <pre>
	 * 获取账号所有tokenId
	 * </pre>
	 * @param account 账号
	 * @param appType 账号类别
	 * @return
	 * @throws ServiceException
	 */
	List<String> getAppTokenIdsListByParam(Integer merchantId, String account, Byte appType) throws ServiceException;
	
	/**
	 * 通过账号删除session
	 * @param account
	 * @param appType
	 */
	void deleteByAccount(Integer merchantId, String account, Byte appType)throws ServiceException;
	
}