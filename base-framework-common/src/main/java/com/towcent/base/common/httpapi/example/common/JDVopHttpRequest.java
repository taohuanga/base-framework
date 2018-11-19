package com.towcent.base.common.httpapi.example.common;

import static com.towcent.base.common.utils.BaseHttpClient.sendHttpsMsg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.towcent.base.common.httpapi.AbstractHttpRequest;
import com.towcent.base.common.httpapi.model.RequestBody;
import com.towcent.base.common.httpapi.model.RequestHeader;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.DateUtils;
import com.towcent.base.common.utils.Md5Utils;

/**
 * 京东Vop请求封装
 * 
 * @author huangtao
 *
 */
public abstract class JDVopHttpRequest extends AbstractHttpRequest {
	
	@Resource
	protected RedisTemplateExt<String, Object> redisTemplateExt;
	
	@Resource
	protected JDHttpConfig config;
	
	/** 对接的存储token的key */
	public static final String TOKEN_KEY = "token_jdvop";
	/** 对接的Code */
	public static final String QM_CODE = "code";
	/** 对接的state */
	public static final String QM_STATE = "state";
	/** 对接的access_token */
	public static final String QM_ACCESS_TOKEN = "access_token";
	/** 对接的expires_in */
	public static final String QM_EXPIRES_IN = "expires_in";
	/** 对接的time */
	public static final String QM_TIME = "time";
	/** 对接的refresh_token */
	public static final String QM_REFRESH_TOKEN = "refresh_token";
	
	
	@Override
	protected RequestHeader buildRequestHeader() {
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setRequestDataType("json");
		return requestHeader;
	}
	
	@Override
	protected String getToken(RequestBody request) {
		String access_token = "";
		// 检查code stat并且去申请token
		String access_token_json = getValue(TOKEN_KEY);
		if (StringUtils.isNotEmpty(access_token_json)) {
			JSONObject jsonObj = JSON.parseObject(access_token_json);
			// 先判断是否过期
			if (System.currentTimeMillis() - jsonObj.getLongValue(QM_TIME)
					- jsonObj.getLongValue(QM_EXPIRES_IN) * 1000 > 0) {
				// 过期通过刷新接口刷新token
				access_token = refreshToken(request, jsonObj.getString(QM_REFRESH_TOKEN));
			} else {
				access_token = jsonObj.getString(QM_ACCESS_TOKEN);
			}
		} else {
			String time = DateUtils.formatDateTime(new Date());
			// 拼接sign字符串 client_secret+timestamp+client_id+username+password+grant_type+scope+client_secret
			StringBuffer signBf = new StringBuffer(config.getClientSecret());
			signBf.append(time).append(config.getClientId()).append(config.getUserName());
			signBf.append(config.getPassWord()).append("access_token").append(config.getClientSecret());
			
			// 调用京东接口获取token
			StringBuffer tokenUrl = new StringBuffer(config.getBaseUrl());
			tokenUrl.append("oauth2/accessToken?grant_type=access_token");
			tokenUrl.append("&client_id=").append(config.getClientId());
			tokenUrl.append("&client_secret=").append(config.getClientSecret());
			tokenUrl.append("&timestamp=").append(UrlEncode(time));
			tokenUrl.append("&username=").append(UrlEncode(config.getUserName()));
			tokenUrl.append("&password=").append(config.getPassWord());
			tokenUrl.append("&scope=");
			tokenUrl.append("&sign=").append(Md5Utils.encryption(signBf.toString()).toUpperCase());
			
			access_token = sendForResult(request, tokenUrl);
		}

		return access_token;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleRequestUrl(RequestBody request) {
		String access_token = "";
        StringBuffer sb = new StringBuffer(config.getBaseUrl());
        // 获取token
        access_token = getToken(request);
        
		if (StringUtils.isEmpty(access_token)) {
			access_token = config.getAccessToken();
		}
        sb.append(this.getHttpMethodName());
        sb.append("?token=").append(access_token);
        // 获取请求体中设置的参数
        Map<String, Object> pMap = JSON.parseObject(JSON.toJSONString(request), Map.class);
        Set<String> keySet = pMap.keySet();
		for (String key : keySet) {
			sb.append("&").append(key);
			sb.append("=");
			sb.append(UrlEncode(MapUtils.getString(pMap, key, "")));
		}
        logger.debug("组装京东调用的接口地址为：" + sb.toString());
        return sb.toString();
	}
	
    /**
     * 获取token参数和code, code只能使用一次
     * 
     * @param key 
     * @return
     */
    private String getValue(String key)
    {
        String value = "";
        // Object obj = redisTemplateExt.get(key);
        Object obj = TokenRedisUtil.getValue(key);
        if (null != obj)
        {
            JSONObject jsonObj = JSON.parseObject(obj.toString());
            value = jsonObj.toJSONString();
        }
        return value;
    }
    
    /**
     * token过期，通过刷新token获取最新的token
     * 
     * @param request 请求参数
     * @param refreshToken 刷新token的令牌
     * @return
     */
    private String refreshToken(RequestBody request, String refreshToken) {
        // 组装申请新的url
        StringBuffer tokenUrl = new StringBuffer(config.getBaseUrl());
        tokenUrl.append("oauth2/refreshToken?refresh_token=").append(refreshToken);
        tokenUrl.append("&client_id=").append(config.getClientId());
        tokenUrl.append("&client_secret=").append(config.getClientSecret());
        
        // 发送请求到jos服务器获取token
        return sendForResult(request, tokenUrl);
    }
    
    /**
     * 发送请求到京东服务器获取token
     * 
     * @param request 请求参数
     * @param tokenUrl 请求url
     * @return
     */
	private String sendForResult(RequestBody request, StringBuffer tokenUrl) {
		String access_token = "";
		// 调用获取token
		final String tonke_json = sendHttpsMsg(tokenUrl.toString(), "", buildRequestHeader().getContentType(),
				buildRequestHeader().getContentEncoding());
		logger.debug("请求京东服务器返回数据为：" + tonke_json);
		
		if (StringUtils.isNotEmpty(tonke_json)) {
			JSONObject jsObject = JSON.parseObject(tonke_json);
			if (null != jsObject.get("result") && jsObject.get("resultCode").toString().equals("0000")
					&& jsObject.getString("success").equals("true")) {
				JSONObject jsObject1 = JSON.parseObject(jsObject.get("result").toString());
				String expires_in = jsObject1.get(QM_EXPIRES_IN).toString();
				// 解析返回数据
				access_token = jsObject1.getString(QM_ACCESS_TOKEN);

				if (StringUtils.isNotEmpty(access_token)) {
					// 异步 设置到缓存和内存
					TokenRedisUtil.set(TOKEN_KEY, Long.parseLong(expires_in), jsObject1.toJSONString());
					// redisTemplateExt.set(TOKEN_KEY, jsObject1.toJSONString(), Long.parseLong(expires_in));
				} else {
					// 获取失败需要邮件或者短信通知指定的人员处理
					logger.info("京东返回数据为：" + tonke_json);
				}
			}
		} else {
			// 获取失败需要邮件或者短信通知指定的人员处理
			logger.info("京东返回数据为：" + tonke_json);
		}
		return access_token;
	}
	
	private String UrlEncode(String val) {
		try {
			return URLEncoder.encode(val, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("URLEncoder转义出错", e);
		}
		return val;
	}
}
