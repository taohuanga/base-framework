/*
 * TODO: 增加描述
 * 
 * @author huangtao
 * @date 2014年6月9日 下午5:37:15
 * @version 0.1.0
 * @copyright yougou.com 
 */

package com.towcent.base.common.utils;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_UP;

import java.math.BigDecimal;

/**
 * 提供相对精确的算法
 * 
 * @author huangtao
 * @date 2014年6月9日 下午5:37:15
 * @version 0.1.0 
 */
public class PreciseCompute {
	
	/**
	 * 计算精度  （小数位）
	 */
	public static final int SCALE = 2;
	
	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	 * 定精度，由roundingMode参数指定舍入模式
	 * @param b1 被除数
	 * @param b2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。 
	 * @param roundingMode 舍入模式 
	 * @return 两个参数的商 
	 */
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale, int roundingMode) {
		if (scale < 0) {
		    throw new IllegalArgumentException(  
		      "The scale must be a positive integer or zero");  
		}
		return b1.divide(b2, scale, roundingMode);
	}
	
	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	 * 定精度，以后的数字四舍五入。 
	 * @param b1 被除数 
	 * @param b2 除数 
	 * @param scale 表示表示需要精确到小数点以后几位。 
	 * @return 两个参数的商 
	 */  
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
	   return div(b1, b2, scale, ROUND_HALF_UP);
	} 
	
	/** 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
	 * 小数点以后2位，以后的数字四舍五入。 
	 * @param v1 被除数 
	 * @param v2 除数 
	 * @return 两个参数的商 
	 */  
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
	   return div(v1, v2, SCALE);
	}  
	
	public static BigDecimal div(BigDecimal v1, Integer v2) {
		return div(v1, BigDecimal.valueOf(v2));
	}  
	
	/*=================================乘法运算========================================*/
	
	/**
	 * 提供（相对）精确的乘法运算
	 * 
	 * @param b1  乘数
	 * @param b2  被乘数
	 * @return  两个参数的乘积
	 */
	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
		return b1.multiply(b2);
	}
	
	public static BigDecimal multiply(BigDecimal b1, Integer b2) {
		return multiply(b1, BigDecimal.valueOf(b2));
	}
	
	/*=================================尾数处理========================================*/
	
	/**
	 * 提供精确的小数位尾数处理方法
	 * @param b 需要处理的数字
	 * @param scale 保留的小数位数
	 * @param roundingMode 尾数处理的模式(0:进尾 1:去尾 4:四舍五入)
	 * @return 结果
	 */
	public static BigDecimal round(BigDecimal b, int scale, int roundingMode) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		return b.setScale(scale, roundingMode);
	}
	
	/** 
	 * 提供四舍五入处理。 
	 * @param b  
	 * @param scale 小数点后保留几位 
	 * @return 四舍五入后的结果 
	 */  
	public static BigDecimal round(BigDecimal b) {
	   return round(b, SCALE);
	}
	
	/** 
	 * 提供四舍五入处理。 
	 * @param b  
	 * @param scale 小数点后保留几位 
	 * @return 四舍五入后的结果 
	 */  
	public static BigDecimal round(BigDecimal b, int scale) {
	   return round(b, scale, ROUND_HALF_UP);
	}
	
	/**
	 * 提供去尾处理
	 * @param b 
	 * @param scale 小数点后保留几位
	 * @return 去尾后的结果
	 */
	public static BigDecimal roundDown(BigDecimal b, int scale) {
		return round(b, scale, ROUND_DOWN);
	}
	
	/**
	 * 提供进尾处理
	 * @param b 
	 * @param scale 保留的小数位数 
	 * @return 进尾后的结果
	 */
	public static BigDecimal roundCarry(BigDecimal b, int scale) {
		return round(b, scale, ROUND_UP);
	}
	
	
	public static void main(String[] args) {
		BigDecimal b1 = new BigDecimal(23.445);
		
		BigDecimal b2 = new BigDecimal(23.448);
		
		System.out.println("四舍五入：" + round(b1, 2));
		System.out.println("去尾：" + roundDown(b1, 2));
		System.out.println("进尾：" + roundCarry(b2, 2));
		
		BigDecimal v1 = new BigDecimal(100);
		BigDecimal v2 = new BigDecimal(3);
		BigDecimal v3 = div(v1, v2, 0, ROUND_DOWN);
		System.out.println(v3);
	}
}
