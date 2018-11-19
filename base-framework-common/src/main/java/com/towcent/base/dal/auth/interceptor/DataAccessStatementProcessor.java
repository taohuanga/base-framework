package com.towcent.base.dal.auth.interceptor;

import org.apache.ibatis.reflection.MetaObject;

/**
 * 
 * 数据权限逻辑处理接口
 * 
 * @author huangtao
 * @date 2016年9月25日 下午2:58:42
 * @version 0.1.0 
 */
public interface DataAccessStatementProcessor {
	String process(MetaObject metaObject);
}
