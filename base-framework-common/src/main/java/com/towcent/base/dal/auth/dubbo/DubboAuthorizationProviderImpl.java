package com.towcent.base.dal.auth.dubbo;

import com.towcent.base.dal.auth.AuthorizationProvider;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import java.util.List;

public class DubboAuthorizationProviderImpl implements AuthorizationProvider {	
	protected static final XLogger logger = XLoggerFactory
			.getXLogger(DubboAuthorizationProviderImpl.class);


	@Override
	public void clear() { 
		/*RpcContext.getContext().remove("user");*/
	}


	@Override
	public List<String> getRoles() {
		return null;
	}
	
}
