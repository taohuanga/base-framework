package com.towcent.base.dal.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.Properties;

@Intercepts( {
    @Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class
    })
})
public class UpdateSqlInterceptor implements Interceptor {

  private static final XLogger LOGGER = XLoggerFactory.getXLogger(UpdateSqlInterceptor.class);

  public Object intercept(Invocation invocation) throws Throwable {
    String sqlStr = "";
    try {
      Object[] updateArgs = invocation.getArgs();
      MappedStatement statement = (MappedStatement) updateArgs[0];
      //Object parameterObj = updateArgs[1];
      BoundSql sql = statement.getSqlSource().getBoundSql(null);
      sqlStr = sql.getSql();
    } catch (Exception e) {
      LOGGER.error("Exception in update SQL interceptor during retrieving SQL:", e);
    }
    boolean isValid = UpdateSqlUtils.isSqlSubmittable(sqlStr);
    if (isValid) {
      return invocation.proceed();
    } else {
      throw new IllegalStateException("Invalid update SQL in Wonhigh DML semantic:" + sqlStr);
    }
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties) {

  }

}
