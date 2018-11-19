package com.towcent.base.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.enums.RuleTypeEnum;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysDictData;
import com.towcent.base.common.model.JsSysDictType;
import com.towcent.base.common.model.SysImageConf;
import com.towcent.base.common.model.SysWxConfig;
import com.towcent.base.common.utils.Assert;
import com.towcent.base.common.utils.ImageUtils;
import com.towcent.base.manager.BaseCommonApi;
import com.towcent.base.service.CodingRuleService;
import com.towcent.base.service.JsSysDictDataService;
import com.towcent.base.service.JsSysDictTypeService;
import com.towcent.base.service.SysImageConfService;
import com.towcent.base.service.SysWxConfigService;

@Service
public class BaseCommonApiImpl implements BaseCommonApi {
	
	@Resource
	private JsSysDictTypeService jsSysDictTypeService;
	@Resource
	private JsSysDictDataService jsSysDictDataService;
	@Resource
	private SysImageConfService imageConfService;
	@Resource 
	private CodingRuleService codingRuleService;
	@Resource
	private SysWxConfigService sysWxConfigService;
	
	@Override
	public List<JsSysDictData> getDictListByKey(Integer merchantId, String key) throws RpcException {
		Assert.isNotEmpty(key, "字典key不能为空");
		
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("dictType", key);
			params.put("status", BaseConstant.DEL_FLAG_0);
			JsSysDictType jsSysDictType = jsSysDictTypeService.findOne(params);
			if (null==jsSysDictType) {
				return null;
			}
			
			Map<String, Object> params1 = Maps.newHashMap();
			params1.put("dictType", jsSysDictType.getDictType());
			params1.put("corpCode", merchantId);
			params1.put("status", BaseConstant.DEL_FLAG_0);
			List<JsSysDictData> list = jsSysDictDataService.findByBiz(params1);
			return list;
		} catch (ServiceException e) {
			throw new RpcException("", "", e);
		}
	}
	
	@Override
	public Map<String, JsSysDictData> getDictMapByKey(Integer merchantId, String key) throws RpcException {
		List<JsSysDictData> list = this.getDictListByKey(merchantId, key);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		Map<String, JsSysDictData> map = Maps.newHashMap();
		for (JsSysDictData dtl : list) {
			map.put(dtl.getDictValue(), dtl);
		}
		return map;
	}
	
	@Override
	public JsSysDictData getDictByKeyVal(Integer merchantId, String key, String val) throws RpcException {
		Map<String, JsSysDictData> map = this.getDictMapByKey(merchantId, key);
		if (MapUtils.isEmpty(map)) {
			return null;
		}
		return (JsSysDictData) MapUtils.getObject(map, val, null);
	}
	
	@Override
	public JsSysDictData getDictByKeyName(Integer merchantId, String key, String name) throws RpcException {
		List<JsSysDictData> list = getDictListByKey(merchantId, key);
		JsSysDictData dict = null;
		if (!CollectionUtils.isEmpty(list)) {
			for (JsSysDictData sysDictDtl : list) {
				if (name.equals(sysDictDtl.getDictLabel())) {
					dict = sysDictDtl;
					break;
				}
			}
		}
		return dict;
	}
	
	@Override
	public SysImageConf getImageConfByType(Integer merchantId, Integer type) throws RpcException {
		Assert.notNull(type, "图片类型不能为空");
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("imageType", type);
			params.put("merchantId", merchantId);
			List<SysImageConf> list = imageConfService.findByBiz(params);
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
		} catch (ServiceException e) {
			throw new RpcException("", "", e);
		}
	}
	
	@Override
	public String getImageRelativePath(Integer merchantId, Integer type) throws RpcException {
		SysImageConf imageConf = this.getImageConfByType(merchantId, type);
		return ImageUtils.buildImageRelativePath("/", imageConf);
	}
	
	@Override
	public String getSerialNo(Integer merchantId, RuleTypeEnum type) throws RpcException {
		try {
			return codingRuleService.getSerialNo(type, merchantId);
		} catch (ServiceException e) {
			throw new RpcException("", "", e);
		}
	}
	
	@Override
	public String getSerialNo(Integer merchantId, String requestId) throws RpcException {
		try {
			return codingRuleService.getSerialNo(requestId, merchantId);
		} catch (ServiceException e) {
			throw new RpcException("", "", e);
		}
	}
	
	@Override
	public List<SysWxConfig> getSysWxConfigListByParam(Map<String, Object> params) throws RpcException {
		try {
			return sysWxConfigService.findByBiz(params);
		} catch (ServiceException e) {
			throw new RpcException("", "", e);
		}
			
	}
}
