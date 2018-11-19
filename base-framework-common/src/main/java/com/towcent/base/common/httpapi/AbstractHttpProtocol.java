package com.towcent.base.common.httpapi;

import static com.towcent.base.common.utils.BaseHttpClient.sendHttpMsg;
import static com.towcent.base.common.utils.BaseHttpClient.sendHttpsMsg;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.httpapi.model.RequestBody;
import com.towcent.base.common.httpapi.model.RequestHeader;
import com.towcent.base.common.httpapi.model.Response;
import com.towcent.base.common.mapper.JaxbMapper;

/**
 * 抽象的http协议的封装
 * @author huangtao
 *
 */
public abstract class AbstractHttpProtocol {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final Executor EXECUTER = Executors.newFixedThreadPool(8);
	
	/**
	 * 构造请求头
	 * @return
	 */
	protected abstract RequestHeader buildRequestHeader();
	
	/**
	 * 组装请求的URL地址
	 * 
	 * @param request
	 * @return
	 */
	protected abstract String handleRequestUrl(RequestBody request);
	
	/**
	 * 组装请求体
	 * 
	 * @param request
	 * @return
	 */
	public String handleRequestBody(RequestBody request) {
		String rs = "";
		logger.debug("api请求为：" + request.toString());
		
		if (null != request) {
			if (this.isFormatJson()) {
				rs = JSON.toJSON(request).toString();
			} else {
				rs = JaxbMapper.toXml(request, BaseConstant.UTF8);
			}
		}
		return rs;
	}
	
	/**
	 * 基本调用封装
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response invoke(RequestBody request, Response response) throws Exception {
		if (null == request) {
			logger.debug("传入参数为空，无效的请求");
			return null;
		}
		logger.debug("api调用，传入参数为：" + request.toString());
		
		RequestHeader requestHeader = buildRequestHeader();
		if (null != requestHeader) {
			logger.debug(StringUtils.center("", 60, "*"));
			
			// 1. 组装URL
			String url = this.handleRequestUrl(request);
			logger.debug("请求组装的url地址为：" + url);
			logger.debug(StringUtils.center("", 60, "*"));
			
			// 2. 设置请求体
			String body = this.handleRequestBody(request);
			
			// 3. HTTP调用
			String responseString = "";
			if (StringUtils.startsWith(url, "http://")) {
				responseString = sendHttpMsg(url, body, requestHeader.getContentType(),
						requestHeader.getContentEncoding());
			} else {
				responseString = sendHttpsMsg(url, body, requestHeader.getContentType(),
						requestHeader.getContentEncoding());
			}
			// 4. 转换为对象
			if (StringUtils.isNotEmpty(responseString)) {
				if (this.isFormatJson()) {
					response = JSON.parseObject(responseString,	response.getClass());
					
					// 设置方法
					// response.setRequestMethod(requestHeader.getRequestMethod());
					handleResponse(response);
				} else {
					// 暂时没有实现(XML)
					response = JaxbMapper.fromXml(responseString, response.getClass());
				}
			}
		}
		return response;
	}
	
	/**
	 * 异步业务处理类(获取到请求的响应)
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private void handleResponse(final Response response) {
		logger.debug("api响应为：" + response.toString());
		
		EXECUTER.execute(new Runnable() {
			@Override
			public void run() {
				logger.debug("api调用返回的响应为：" + response.toString());
				if (StringUtils.isNotEmpty(response.getRequestMethod())) {
					// TODO baseApiService.handleResponse(response);
				}
			}
		});

	}
	
	/**
	 * 判断请求格式是否为json
	 * @param request
	 * @return json:true xml:false
	 */
	private boolean isFormatJson() {
		RequestHeader requestHeader = buildRequestHeader();
		return StringUtils.equalsIgnoreCase(requestHeader.getRequestDataType(), BaseConstant.JSON);
	}
}
