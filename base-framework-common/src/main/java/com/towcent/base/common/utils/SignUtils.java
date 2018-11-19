package com.towcent.base.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.mapper.JsonMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;

/**
 * 签名算法
 * 
 * @author huangtao
 * @date 2015年6月25日 下午3:45:04
 * @version 0.1.0 
 */
public class SignUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(SignUtils.class);
	
	private static Map<String, String> akeySecretMap = Maps.newHashMap();
	
	static {
		akeySecretMap.put("227456", "6284561c787b4cfba35b6f33a4909c97");
		akeySecretMap.put("888888", "44d6d569341947ec947c711a18574de5");
	}
	
	public static String getSecretByKey(String appKey) {
		return MapUtils.getString(akeySecretMap, appKey);
	}
	
	/**
	 * 签名生成算法
	 * @param params 请求参数集，所有参数必须已转换为字符串类型
	 * @param secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(Map<String, String> params, String secret) throws IOException
	{
	    Set<Entry<String, String>> entrys = params.entrySet();
	 
	    // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
	    StringBuilder basestring = new StringBuilder();
	    for (Entry<String, String> param : entrys) {
	    	String value = param.getValue();
	    	if (StringUtils.startsWith(value, "[") && StringUtils.endsWith(value, "]")) {
	    		value = toListString(value);
	    	}
	        basestring.append(param.getKey()).append("=").append(value);
	    }
	  
	    basestring.append(secret);
	    logger.debug("待签名的串：" + basestring.toString());
	    
	    // 使用MD5对待签名串求签
	    byte[] bytes = null;
	    try {
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        String baseValue =  basestring.toString();//.replace(" ", "");
	        bytes = md5.digest(baseValue.getBytes("UTF-8"));
	    } catch (GeneralSecurityException ex) {
	        throw new IOException(ex);
	    }
	 
	    // 将MD5输出的二进制结果转换为小写的十六进制
	    StringBuilder sign = new StringBuilder();
	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(bytes[i] & 0xFF);
	        if (hex.length() == 1) {
	            sign.append("0");
	        }
	        sign.append(hex);
	    }
	    logger.debug("生成的签名摘要串：" + sign.toString());
	    return sign.toString();
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	private static String toListString(String jsonString) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) JsonMapper.fromJsonString(jsonString, List.class);
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder("");
		if (!CollectionUtils.isEmpty(list)) {
			for (Map<String, Object> map : list) {
				Map<String, String> maps = toMapString(map);
				// 先将参数以其参数名的字典序升序进行排序
			    Map<String, String> sortedParams = new TreeMap<String, String>(maps);
			    Set<Entry<String, String>> entrys = sortedParams.entrySet();
			    for (Entry<String, String> param : entrys) {
			        basestring.append(param.getKey()).append("=").append(param.getValue());
			    }
			}
		}
		return basestring.toString();
	}
	
	private static Map<String, String> toMapString(Map<String, Object> map) {
		Map<String, String> maps = Maps.newHashMap();
		if (MapUtils.isNotEmpty(map)) {
			for (String key : map.keySet()) {
				if (BaseConstant.SIGN.equals(key)) {
					continue;
				}
				Object o = map.get(key);
				maps.put(key, String.valueOf(o));
			}
		}
		return maps;
	}
	
	public static Map<String, String> toJsonObject(String json) {
		//先将参数以其参数名的字典序升序进行排序
		Map<String, String> hMap = new TreeMap<String, String>();
		ObjectNode node = (ObjectNode) JsonMapper.fromJsonString(json, ObjectNode.class);
		Iterator<String> it = node.fieldNames();
		while (it.hasNext()) {
			String key = it.next();
			JsonNode obj = node.get(key);
			hMap.put(key, obj.isArray() ? obj.toString() : node.get(key).asText());
		}
		return hMap;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String, Object> map = Maps.newHashMap();
		map.put("deviceType", "1");
		map.put("deviceSn", "xxxxxxxxxxxxxxxxx");
		map.put("imei", "13454657");
		map.put("appVersion", "0.0.1");
		map.put("iosVersion", "7.0.1");
		map.put("appKey", "123456");
		map.put("timestamp", "2015-06-29 12:32:00");
		map.put("memberAccount", "13498765543");
		map.put("password", "12345678");
		map.put("stepList", "[  {      \"id\" : 1,      \"maxNum\" : 1,      \"price\" : 10,      \"minNum\" : 1    },    {      \"id\" : 2,      \"maxNum\" : 2,      \"price\" : 20,      \"minNum\" : 2    }  ]");
		
		Map<String, String> maps = toMapString(map);
		//String sign = getSignature(map, "2122fb48d284fa8a34797ec410347a88");
		System.out.println(maps);
		//System.out.println(sign);
//		String str = "[  {      \"id\" : 1,      \"maxNum\" : 1,      \"price\" : 10,      \"minNum\" : 1    },    {      \"id\" : 2,      \"maxNum\" : 2,      \"price\" : 20,      \"minNum\" : 2    }  ]";
//		String a = toListString(str);
//		System.out.println(a);
//		System.out.println("[sdgdg]".replaceAll("\\[|\\]", ""));
		
	}
}
