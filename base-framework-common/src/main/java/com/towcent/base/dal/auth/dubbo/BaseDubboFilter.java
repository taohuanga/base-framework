package com.towcent.base.dal.auth.dubbo;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDubboFilter implements Filter {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseDubboFilter.class);
	
	protected static final String monitorUrl = "com.alibaba.dubbo.monitor.MonitorService";
	
	protected abstract void preInvoke(Invoker<?> invoker, Invocation invocation);

	protected abstract void afterInvoke(Result result);

	protected void error(RpcException ex) {
		logger.error("dubbo服务异常." + ex);
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		long beginTime = System.currentTimeMillis();
		try {
			preInvoke(invoker,invocation); //暂时关闭

			Result result = invoker.invoke(invocation);
			
			afterInvoke(result);
			
			String url = invoker.getUrl().toString();
			if (url.indexOf(monitorUrl) < 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(invoker.getUrl()).append(" takes:").append(System.currentTimeMillis() - beginTime)
						.append("ms.");
				logger.debug(sb.toString());
			}
			return result;
		} catch (RpcException e) {
			error(e);
			throw e;
		}
	}
}
