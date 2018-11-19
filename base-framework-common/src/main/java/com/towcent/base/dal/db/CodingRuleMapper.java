package com.towcent.base.dal.db;

import com.towcent.base.common.annotation.MyBatisDao;
import com.towcent.base.common.exception.DaoException;
import com.towcent.base.common.model.CodingRule;
import org.apache.ibatis.annotations.Param;

/**
 * 
 */
@MyBatisDao
public interface CodingRuleMapper extends CrudMapper {
	public CodingRule selectByPrimaryKey(@Param("id")int id, @Param("dbName")String dbName) throws DaoException;
	public CodingRule selectByRequestId(@Param("requestId")String requestId, @Param("merchantId")Integer merchantId, @Param("dbName")String dbName) throws DaoException;
	public int resetCurrentSerialNo(@Param("requestId")String requestId, @Param("step")int step, @Param("merchantId")Integer merchantId, @Param("dbName")String dbName) throws DaoException;
	public int increaseSerialNo(@Param("requestId")String requestId, @Param("step")int step, @Param("merchantId")Integer merchantId) throws DaoException;
}