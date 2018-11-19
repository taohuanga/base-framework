package com.towcent.base.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 编码规则工具类
 * 
 * @author wang.dw
 * @date 2014-10-21 下午3:49:10
 * @version 0.9.9 
 */
public class CodingRuleUtil {
	/**
	 *  根据resetMode设置格式话的时间串
	 * @param resetMode
	 */
	public static String initFormatedDate(int resetMode) {
		DateFormat df = new SimpleDateFormat(getFormatPattern(resetMode));
		return df.format(new Date());
	}
	
	/**
	 *  根据resetMode设置格式话的时间串
	 * @param resetMode
	 */
	public static String getFormatedDate(int resetMode , Date date) {
		if(date == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(getFormatPattern(resetMode));
		return df.format(date);
	}
	
	/**
	 * @param resetMode (0:永不重置 1:按天重置 2:按月重置 3:按年重置)
	 * @return
	 */
	public static String getFormatPattern (int resetMode) {
		String pattern = "";
		switch(resetMode) {
			case 1:
				pattern = "yyyyMMdd";
				break;
			case 2:
				pattern = "yyyyMM";
				break;
			case 3:
				pattern = "yyyy";
				break;
		}
		return pattern;
	}
	
	public static void main(String[] args) {
		int resetMode = 1;
		Date now = new Date();
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 00, 00);
		Date date1 = cal.getTime();
		String resetTime = CodingRuleUtil.getFormatedDate(resetMode, date1);
		
		//dbTime为数据库时间，不可能为空
		String dbTime = CodingRuleUtil.getFormatedDate(resetMode, now);
		System.out.println("resetTime:" + resetTime);
		System.out.println("dbTime:" + dbTime);
		
		//resetTime为空就必须reset,
		if("".equals(resetTime) || !dbTime.equals(resetTime)) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}

		
	}
}
