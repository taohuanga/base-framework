package com.towcent.base.service.impl;

import com.google.common.collect.Maps;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.page.SimplePage;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.service.BaseCrudService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author HuangTao
 * @version 2015年5月25日
 */
public abstract class BaseCrudServiceImpl implements BaseCrudService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private CrudMapper mapper;
	
	@PostConstruct
	private void initConfig() {
		this.mapper = init();
	}
	
	public abstract CrudMapper init();
	
	@Override
	public <T> int deleteById(Object id) throws ServiceException {
		try {
			return mapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@Override
	public <T> int add(T entity) throws ServiceException {
		try {
			return mapper.insert(entity);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public <T> int addSelective(T entity) throws ServiceException {
		try {
			return mapper.insertSelective(entity);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@Override
	public <T> T findOne(Map<String, Object> params) throws ServiceException {
		try {
			return (T) mapper.selectOne(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@Override
	public <T> T findById(Object id) throws ServiceException {
		try {
			return (T) mapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@Override
	public <T> List<T> findByBiz(Map<String, Object> params) throws ServiceException {
		try {
			return mapper.selectByParams(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public <T> List<T> findByKeyVal(String key, Object val) throws ServiceException {
		try {
			Map<String, Object> params = Maps.newHashMap();
			if (StringUtils.isNotBlank(key)) {
				params.put(key, val);
			}
			return mapper.selectByParams(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@Override
	public <T> T findByKeyValSingle(String key, Object val) throws ServiceException {
		List<T> list = this.findByKeyVal(key, val);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public <T> int modifyById(T entity) throws ServiceException {
		try {
			return mapper.updateByPrimaryKey(entity);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public <T> int modifyByIdSelective(T entity) throws ServiceException {
		try {
			return mapper.updateByPrimaryKeySelective(entity);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@Override
	public int findCount(Map<String,Object> params) throws ServiceException {
		try {
			return mapper.selectCount(params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public <T> List<T> findByPage(SimplePage page) throws ServiceException {
		try {
			return mapper.selectByPage(page);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

}
