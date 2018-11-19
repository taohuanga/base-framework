package com.towcent.base.common.enums;

import java.util.Calendar;

public enum CacheExpireEnum {

	/**
	 * 五分钟
	 */
	FIVE_MINUTE(  5 * 60 * 1000L ),
	/**
	 * 十分钟
	 */
	TEN_MINUTE( 10 * 60 * 1000L ),
	/**
	 * 半个小时
	 */
	THIRTY_MINUTE(30 * 60 * 1000L),
	/**
	 * 一个小时
	 */
	ONE_HOUR(60 * 60 * 1000L),
	/**
	 * 二个小时
	 */
	TWO_HOUR(2 * 60 * 60 * 1000L),
	/**
	 * 十二小时
	 */
	TWELVE_HOUR(12 * 60 * 60 * 1000L),
	/**
	 * 一天
	 */
	ONE_DAY(24 * 60 * 60 * 1000L),
	/**
	 * 今天12点为止
	 */
	TODAY(-1L)
	;
	
	private long expire;
	
	CacheExpireEnum(long expire){
		this.expire = expire;
	}

	public long getExpire(){
		if(CacheExpireEnum.TODAY.equals(this)){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.add(Calendar.DAY_OF_MONTH, 1);
			return c.getTimeInMillis()-System.currentTimeMillis();
		}
		return expire;
	}
	
}
