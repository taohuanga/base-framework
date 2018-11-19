package com.towcent.base.common.utils;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 短信工具类
 * 
 * @author huangtao
 * @date 2015年6月30日 下午5:17:28
 * @version 0.1.0 
 */
public final class SMSUtils {
	
	public static final String NUMBER_STR = "1234567890";
	
	public static final String STR = "1234567890abcdefghijkmnpqrstuvwxyz";
	
    // 短信类别, 短信模板
    public static Map<Byte, String> MAP = Maps.newConcurrentMap(); 
	
	/**
	 * 生成指定长度的短信验证码
	 * @param length 
	 * @return
	 */
	public static String createRandom(int length) {
		return createRandom(true, length);
	}
	
	/**
	 * 创建指定长度的随机字符串
	 * 
	 * @param numberFlag 是否是数字
	 * @param length 验证码长度
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		StringBuilder sb = new StringBuilder();
		String strTable = numberFlag ? NUMBER_STR : STR;
		int len = strTable.length();
		boolean bDone = true;
		do {
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				sb.append(strTable.charAt(intR));
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return sb.toString();
	}
	
    /**
     * 按类别获取短信模板 
     * @param type 
     * @return
     */
    public static String getSmsTemplateByType(Byte type) {
    	return MapUtils.getString(MAP, type);
    }
    
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(createRandom(6));			
		}
	}
}
