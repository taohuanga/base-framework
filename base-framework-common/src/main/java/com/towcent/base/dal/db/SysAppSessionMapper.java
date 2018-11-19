package com.towcent.base.dal.db;

import org.apache.ibatis.annotations.Param;

import com.towcent.base.common.annotation.MyBatisDao;

/**
 * sys_app_session数据库操作接口
 * 
 * @author Generator
 * @date 2016-03-25 20:15:51
 * @version 1.0.0
 * @copyright facegarden.com
 */
@MyBatisDao
public interface SysAppSessionMapper extends CrudMapper {

	public void deleteByAccount(@Param("account")String account, @Param("appType")Byte appType, @Param("merchantId")Integer merchantId);
}