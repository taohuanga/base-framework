/**
 * Copyright &copy; 2012-2014  All rights reserved.
 */
package com.towcent.base.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyyMMddHHmmss"};

	/**
	 * 将时间格式化成: "yyyyMM"
	 *
	 * @param date
	 * @return "201406"
	 */
	public static String formatMonth(Date date) {
		return formatDate(date, "yyyyMM");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDateTime(new Date());
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis() - date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期相加
	 * @param date Date
	 * @param day int
	 * @return Date
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * @param date Date
	 * @param date1 Date
	 * @return int
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 根据日期判断当月第几周
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getWeek(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//第几周
		int week = calendar.get(Calendar.WEEK_OF_MONTH);
		if (week == 1) {
			return "一";
		} else if (week == 2) {
			return "二";
		} else if (week == 2) {
			return "三";
		} else {
			return "四";
		}
	}

	/**
	 * 获取前一周
	 */
	public static String getLastWeek() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		return formatDate(c.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取前一月
	 */
	public static String getLastMon() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		return formatDate(c.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取前三月
	 */
	public static String getThreeMon() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 3);
		return formatDate(c.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取当前时间, 并格式成："yyyyMMddHHmmss" 输出
	 * 
	 * @return "20140627164632"
	 */
	public static String getCurrentDateTime2Str2() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}
	
	/**
	 * 获取日期的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		return c1.getTime();
	}
	
	/**
	 * 获取日期的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		c1.set(Calendar.SECOND, 59);
		c1.set(Calendar.MILLISECOND, 999);
		return c1.getTime();
	}
	
	/**
	 * 将当前时间的时分秒设置为0.
	 * @Title transformDateStart
	 * @param date
	 * @return
	 */
	public static Date transformDateStart(Date date) {
		if (null == date) {
			return null;
		}
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		return c1.getTime();
	}
	
	/**
	 * 将当前时间的时分秒设置为23:59:59.
	 * @Title transformDateEnd
	 * @param date
	 * @return
	 */
	public static Date transformDateEnd(Date date) {
		if (null == date) {
			return null;
		}
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 23);
		c1.set(Calendar.MINUTE, 59);
		c1.set(Calendar.SECOND, 59);
		return c1.getTime();
	}
	
	public static Date getDiff30(Date date) {
		return getDateDiff(date, 30); // 取当前时间的30分钟
	}
	
	/**
	 * 使用时间往前推minute分钟.
	 * @Title getDiff 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date getDateDiff(Date date, int minute) {
		if (null == date) {
			return null;
		}
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date);
		c1.add(Calendar.MINUTE, -minute); // 取当前时间的30分钟
		return c1.getTime(); 
	}
	
	/**
	 * 获取时间当月最的时间 31号 23:59:59.
	 * @Title getDateMonthMin
	 * @param d
	 * @return
	 */
	public static Date getDateMonthMax(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int maximunDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
		cal.set(Calendar.DAY_OF_MONTH, maximunDay);
		//设置当前时刻的时钟为0
		cal.set(Calendar.HOUR_OF_DAY, 23);
		//设置当前时刻的分钟为0
		cal.set(Calendar.MINUTE, 59);
		//设置当前时刻的秒钟为0
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	/**
	 * 获取时间当月最的时间 1号 00:00:01.
	 * @Title getDateMonthMin
	 * @param d
	 * @return
	 */
	public static Date getDateMonthMin(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//设置当前时刻的时钟为0
		cal.set(Calendar.HOUR_OF_DAY, 0);
		//设置当前时刻的分钟为0
		cal.set(Calendar.MINUTE, 0);
		//设置当前时刻的秒钟为0
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
     * @param args
     * @throws ParseException
     */
	public static void main(String[] args) throws ParseException {
		System.out.println(formatDateTime(getDiff30(new Date())));
	}
}
