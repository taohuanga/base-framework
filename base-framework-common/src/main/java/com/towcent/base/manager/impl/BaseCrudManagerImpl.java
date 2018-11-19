package com.towcent.base.manager.impl;

import com.google.common.collect.Maps;
import com.towcent.base.common.enums.CommonOperatorEnum;
import com.towcent.base.common.exception.ManagerException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.mapper.JsonMapper;
import com.towcent.base.common.page.SimplePage;
import com.towcent.base.common.utils.BeanUtilsCommon;
import com.towcent.base.common.utils.Reflections;
import com.towcent.base.manager.BaseCrudManager;
import com.towcent.base.service.BaseCrudService;
import org.apache.commons.collections.MapUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseCrudManagerImpl implements BaseCrudManager {

	private BaseCrudService service;

	@PostConstruct
	protected void initConfig() {
		this.service = this.init();
	}

	protected abstract BaseCrudService init();


	@Override
	public <ModelType> int add(ModelType modelType) throws ManagerException {
		try {
			return this.service.add(modelType);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public <ModelType> ModelType findById(Object id) throws ManagerException {
		try {
			return this.service.findById(id);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public <ModelType> ModelType findOne(Map<String, Object> params) throws ManagerException {
		try {
			return this.service.findOne(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public <ModelType> int modifyById(ModelType modelType) throws ManagerException {
		try {
			return this.service.modifyById(modelType);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.service.findCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public <ModelType> List<ModelType> findByPage(SimplePage page) throws ManagerException {
		try {
			return this.service.findByPage(page);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public <ModelType> List<ModelType> findByBiz(
			Map<String, Object> params) throws ManagerException {
		try {
			return this.service.findByBiz(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public <ModelType> int save(Map<CommonOperatorEnum, List<ModelType>> params) throws ManagerException {
		try {
			int count = 0;
			for (Entry<CommonOperatorEnum, List<ModelType>> param : params.entrySet()) {
				if (param.getKey().equals(CommonOperatorEnum.DELETED)) {
					List<ModelType> list = params.get(CommonOperatorEnum.DELETED);
					if (null != list && list.size() > 0) {
						for (ModelType modelType : list) {
							count += this.service.deleteById(modelType);
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
					List<ModelType> list = params.get(CommonOperatorEnum.UPDATED);
					if (null != list && list.size() > 0) {
						for (ModelType modelType : list) {
							count += this.service.modifyById(modelType);
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
					List<ModelType> list = params.get(CommonOperatorEnum.INSERTED);
					if (null != list && list.size() > 0) {
						for (ModelType modelType : list) {
							this.service.add(modelType);
						}
						count += list.size();
					}
				}
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 生成扩展属性json串
	 * @param modelType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <ModelType> String builderPropertyToJson(ModelType modelType) {
		try {
			// 页面传递过来的扩展属性
			Map<String, Object> eMap = (Map<String, Object>) Reflections.invokeGetter(modelType, "extendPropertys");
			// 对象属性
			Map<String, Object> map = Maps.newHashMap();
			BeanUtilsCommon.object2Map(modelType, map);
			if (MapUtils.isNotEmpty(map)) {
				if (MapUtils.isEmpty(eMap)) {
					eMap = map;
				} else {
					for (String key : map.keySet()) {
						eMap.put(key, map.get(key));
					}
				}				
			}
			return JsonMapper.toJsonString(eMap);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
