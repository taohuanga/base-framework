package com.towcent.base.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
@Lazy(false)
public class IdGen {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	private static final String PATTERN = "yyMMddHHmmss";
	
	private static final String JPG = ".jpg";
	
	/**
	 * 随机生成图片名称
	 * @return
	 */
	public static String randomString() {
		StringBuilder sb = new StringBuilder();
		String sf = DateUtils.getDate(PATTERN);
		sb.append(Math.abs(random.nextInt(9999)));
		while (sb.length() < 4) {
			sb.insert(0, 0);
		}
		sb.insert(0, sf);
		sb.append(JPG);
		return sb.toString();
	}
	
	public static String randomByDate() {
		StringBuffer sb = new StringBuffer(DateFormatUtils.format(new Date(), "yyMMddHHmmssSSSS"));
		//return sb.append(Math.abs(random.nextInt(1000))).toString();
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
		for (int i=0; i<1000; i++){
			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5) + "  " + IdGen.randomString());
		}
	}

}
