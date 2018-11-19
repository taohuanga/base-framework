package com.towcent.base.manager;

import java.util.List;
import java.util.Map;

import com.towcent.base.common.enums.RuleTypeEnum;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysDictData;
import com.towcent.base.common.model.SysImageConf;
import com.towcent.base.common.model.SysWxConfig;

/**
 * 基础公共接口
 * 
 * @author huangtao
 *
 */
public interface BaseCommonApi {
	
	/**
	 * 通过数据字典key获取  字典列表
	 * @param merchantId 商户Id
	 * @param key 例如: sex(性别)
	 * @return [1:男 2:女]
	 * @throws RpcException
	 */
	List<JsSysDictData> getDictListByKey(Integer merchantId, String key) throws RpcException;
	
	/**
	 * 通过数据字典key获取 字典映射表
	 * @param merchantId 商户Id
	 * @param key 例如: sex(性别)
	 * @return map(1->男  2->女)
	 * @throws RpcException
	 */
	Map<String, JsSysDictData> getDictMapByKey(Integer merchantId, String key) throws RpcException;
	
	/**
	 * 通过数据字典key、val 获取具体的字段对象
	 * @param merchantId 商户Id
	 * @param key 例如: sex(性别)
	 * @param val 例如: 1
	 * @return obj -> 1:男
	 * @throws RpcException
	 */
	JsSysDictData getDictByKeyVal(Integer merchantId, String key, String val) throws RpcException;
	
	/**
	 * @Title: getDictByKeyName
	 * @Description: 通过数据字典key、name 获取具体的字段对象.
	 * @param merchantId 商户Id
	 * @param key 例如: sex(性别)
	 * @param name 例如: 男
	 * @return obj -> 1:男
	 * @throws RpcException
	 * @return: SysDictDtl
	 */
	JsSysDictData getDictByKeyName(Integer merchantId, String key, String name) throws RpcException;
	
	/**
	 * 通过类型获取系统图片配置
	 * @param merchantId 商户Id
	 * @param type (0:头像)
	 * @return
	 * @throws RpcException
	 */
	SysImageConf getImageConfByType(Integer merchantId, Integer type) throws RpcException;
	
	/**
	 * 获取图片的相对路径(FTP服务器).
	 * @param merchantId  商户Id
	 * @param type        图片类型
	 * @return
	 * @throws RpcException
	 */
	String getImageRelativePath(Integer merchantId, Integer type) throws RpcException;
	
	/**
	 * 获取编号:
	 * 编码格式为：前綴 + 日期串 + 序列号
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	public String getSerialNo(Integer merchantId, RuleTypeEnum type) throws RpcException;
	
	/**
	 * 获取编号:
	 * 编码格式为：前綴 + 日期串 + 序列号
	 * @param requestId
	 * @return
	 * @throws ServiceException
	 */
	public String getSerialNo(Integer merchantId, String requestId) throws RpcException;
	
	/**
	 * 获取微信公众号配置.
	 * @Title getSysWxConfigListByParam
	 * @param params
	 * @return
	 * @throws RpcException
	 */
	List<SysWxConfig> getSysWxConfigListByParam(Map<String, Object> params) throws RpcException;
}
