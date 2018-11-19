package com.towcent.base.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.towcent.base.common.config.SpringConfig;

/**
 * 缓存Key工具类(抽象的)
 * 
 * @author huangtao
 * @date 2015年7月1日 下午12:35:54
 * @version 0.1.0 
 */
public class BaseCacheKey {

	public static Logger logger = LoggerFactory.getLogger(BaseCacheKey.class);

	/** 缓存字符串前缀 */
	protected static String prefix = "dr";

	static {
		SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
		prefix = config.getRedisPrefix();
		logger.info("redis.prefix ====> " + prefix);
	}

	/** 商品模块 */
	public static final String MODEL_GOODS = "goods";
	/** 系统 */
	public static final String MODEL_SYS = "sys";
	/** 短信 */
	public static final String MODEL_SMS = "sms:";
	
	/** 省市区明细缓存key */
	public static final String SYS_AREA_DTL_KEY = "area:dtl";
	/** 省市区上下级缓存key */
	public static final String SYS_AREA_LEVEL_KEY = "area:level";
	
	/** 系统属性配置项缓存key */
	public static final String SYS_PROPERTY_KEY = "sys:property:key";
	
	/** 缓存key分割符 */
	public static final String COLON = ":";
	
	/** 缓存默认存活时间 1440分钟 */
	public static final long DEFAULT_TIMEOUT = 1440;


	/**
	 * 获取省市区上下级缓存key
	 *
	 * @return
	 */
	public static String getAreaLevelKey() {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(SYS_AREA_LEVEL_KEY);
		return sb.toString();
	}

	/**
	 * 获取省市区明细缓存key
	 *
	 * @return
	 */
	public static String getAreaDtlKey() {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(SYS_AREA_DTL_KEY);
		return sb.toString();
	}
	
	public static String getAreaDtlKey(String id) {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(SYS_AREA_DTL_KEY);
		sb.append(COLON).append(id);
		return sb.toString();
	}
	
	/**
	 * session缓存key
	 * 
	 * @param tokenId 登录标识
	 * @return
	 */
	public static String getSessionKey(String tokenId) {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(MODEL_SYS);
		sb.append(COLON).append("tokenId").append(COLON).append(tokenId);
		return sb.toString();
	}
	
	/**
	 * 创建短信验证码缓存Key
	 * @param smsType 短信类型
	 * @param phone 手机号码 
	 * @return
	 */
	public static String getSmsKey(Byte smsType, String phone) {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(MODEL_SYS);
		sb.append(smsType).append(COLON).append(phone);
		return sb.toString();
	}

	public static String getTempGoodsPicKey(String goodsNo) {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(MODEL_GOODS);
		sb.append(COLON).append("temppic").append(COLON).append(goodsNo);
		return sb.toString();
	}
	
	/**
	 * 创建商品图片缓存key
	 * @param goodsNo 商品编码
	 * @return
	 */
	public static String getGoodsPicKey(String goodsNo) {
		StringBuilder sb = new StringBuilder(prefix);
		sb.append(COLON).append(MODEL_GOODS);
		sb.append(COLON).append("pic").append(COLON).append(goodsNo);
		return sb.toString();
	}

}
