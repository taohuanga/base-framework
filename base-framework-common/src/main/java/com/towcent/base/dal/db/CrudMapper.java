package com.towcent.base.dal.db;

import com.towcent.base.common.page.SimplePage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DAO支持类
 * @author huangtao
 * @date 2015-05-19
 * @param <T>
 */
public interface CrudMapper {
	
	public int deleteByPrimaryKey(Object id);
	
	public <T> int insert(T entity);
	
	public <T> int insertSelective(T entity);
	
	public <T> T selectByPrimaryKey(Object id);
	
	public <T> T selectOne(@Param("params") Map<String, Object> params);
	
	public <T> List<T> selectByParams(@Param("params") Map<String, Object> params);
	
	public <T> int updateByPrimaryKeySelective(T entity);
	
	public <T> int updateByPrimaryKey(T entity);
	
	public int selectCount(@Param("params") Map<String, Object> params);
	
	public <T> List<T> selectByPage(SimplePage page);
}