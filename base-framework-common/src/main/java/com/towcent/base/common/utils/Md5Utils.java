package com.towcent.base.common.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5工具类
 * 
 * @author huangtao
 * @date 2015年7月14日 下午6:17:48
 * @version 0.1.0 
 */
public class Md5Utils {
	
	private final static char hexDigits[] = "0123456789abcdef".toCharArray();
	
	private static final String MD5 = "MD5";
	
	public static String encryption(String passwd) {
		try {
			byte[] strTemp = passwd.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance(MD5);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return passwd;
	}

	public static void main(String[] args) {
		System.out.println(encryption("123456"));
	}

}
