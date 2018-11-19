package com.towcent.base.common.interceptor;

import com.towcent.base.common.annotation.ChangeDataSource;
import com.towcent.base.common.utils.DataSourceSwitch;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * TODO: 增加描述
 * 
 */
public class DataSourceAdvice implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		ChangeDataSource ds = invocation.getMethod().getAnnotation(ChangeDataSource.class);
		ds = ds == null ? invocation.getClass().getAnnotation(ChangeDataSource.class) : ds;
		if (ds != null) {
			DataSourceSwitch.setCurrentDataSource(ds.value());
		}
		try {
			return invocation.proceed();
		} finally {
			if (ds != null) {
				DataSourceSwitch.setCurrentDataSource(null);
			}
		}
	}

}
