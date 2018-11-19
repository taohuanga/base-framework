package com.towcent.base.dal.auth.dubbo;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.springframework.stereotype.Component;

@Component("providerFilter")
@Activate(group = "provider")
public class ProviderSecurityFilter extends BaseDubboFilter implements Filter {

	@Override
	protected void preInvoke(Invoker<?> invoker, Invocation invocation) {
		String merchantId = RpcContext.getContext().getAttachment("merchantId");
		
		logger.debug("dubbo provider merchantId:{0}.", merchantId);
	}
	
	@Override
	protected void afterInvoke(Result result) {
		clear();
	}

	@Override
	protected void error(RpcException ex) {
		clear();
		super.error(ex);
	}
	
	private void clear(){
//		try {
//			RpcContext.getContext().removeAttachment("officeIds");
//		} catch (Exception e) {
//			logger.error("clearn security info from client.",e);
//		}
	}
}
