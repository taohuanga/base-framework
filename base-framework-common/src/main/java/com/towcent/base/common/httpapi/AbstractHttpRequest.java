package com.towcent.base.common.httpapi;

import com.towcent.base.common.httpapi.model.RequestBody;

/**
 * 抽象的http请求的封装
 * 
 * @author huangtao
 *
 */
public abstract class AbstractHttpRequest extends AbstractHttpProtocol {
	
	/**
	 * 请求方法
	 * @return
	 */
	protected abstract String getHttpMethodName();
	
	/**
	 * 获取token授权
	 * @param request
	 * @return
	 */
	protected abstract String getToken(RequestBody request);
	
}
