package com.towcent.base.dal.auth.interceptor;

import com.towcent.base.common.utils.SpringContextHolder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * 
 * 数据权限拦截器
 * 
 * @author huangtao
 * @date 2016年9月25日 下午2:58:15
 * @version 0.1.0 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DataAccessInspector implements Interceptor {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	
	private final static Logger logger = LoggerFactory.getLogger(DataAccessInspector.class);
	
	DataAccessStatementProcessor statementProcessor;

	public DataAccessStatementProcessor getProcessor() {
		String name = "dataAccessStatementProcessor";
		if (statementProcessor != null) {
			return statementProcessor;
		}
		if (SpringContextHolder.containsBean(name)) {
			statementProcessor = (DataAccessStatementProcessor) SpringContextHolder.getBean(name);
		} else {
			statementProcessor = new DefaultDataAccessStatementProcessor();
		}
		return statementProcessor;
	}

	private MetaObject getMappedStatement(Invocation invocation) {
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();

		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);

		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
		// 可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")
				|| metaStatementHandler.hasGetter("target")) {
			Object object = null;
			if (metaStatementHandler.hasGetter("h")) {
				object = metaStatementHandler.getValue("h");
			}
			if (metaStatementHandler.hasGetter("target")) {
				object = metaStatementHandler.getValue("target");
			}
			if (object != null) {
				metaStatementHandler = MetaObject.forObject(object,
						DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
		}

		return metaStatementHandler;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		long startTime = System.currentTimeMillis();
		MetaObject metaObject = getMappedStatement(invocation);
		
		String statement = getProcessor().process(metaObject);

		if (null == statement) { // 没有变化
			return invocation.proceed();
		}
		metaObject.setValue("delegate.boundSql.sql", statement);
		if (logger.isDebugEnabled()) {
			long time = System.currentTimeMillis() - startTime;
			logger.debug("注入数据权限:" + statement + " (" + time + "ms)");
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
