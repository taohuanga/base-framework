package com.towcent.base.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: 增加描述
 * 
 * @author huangtao
 * @date 2016年9月23日 下午3:23:10
 * @version 0.1.0
 */
public class HumpLineTool {

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	/** 下划线转驼峰 */
	public static String lineToHump(String str) {
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
	public static String humpToLine(String str) {
		return str.replaceAll("[A-Z]", "_$0").toLowerCase();
	}

	private static Pattern humpPattern = Pattern.compile("[A-Z]");

	/** 驼峰转下划线,效率比上面高 */
	public static String humpToLine2(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString().toUpperCase();
	}

	private static String regex = "(_BEGIN|_END)$";
	
	/** 驼峰转下划线,效率比上面高 */
	public static String humpToLine3(String str) {
		return humpToLine2(str).replaceAll(regex, "");
	}
	
	private static Pattern dateEndRegex = Pattern.compile("End$");
	
	/**
	 * 判断是否是结束时间
	 * @param field
	 * @return
	 */
	public static boolean isDateEnd(String field) {
		Matcher matcher = dateEndRegex.matcher(field);
		return matcher.find();
	}
	
	public static void main(String[] args) {
//		String lineToHump = lineToHump("f_parent_no_leader");
//		System.out.println(lineToHump);// fParentNoLeader
//		System.out.println(humpToLine(lineToHump));// f_parent_no_leader
//		System.out.println(humpToLine2(lineToHump));// f_parent_no_leader
//		System.out.println(humpToLine3("pordEndDateEnd"));// f_parent_no_leader
//		System.out.println(humpToLine3("pordBeginDateBegin"));// f_parent_no_leader
		String st = "pordEndDateBegin";
		
		Matcher matcher = dateEndRegex.matcher(st);
		System.out.println(matcher.find());
	}

}
