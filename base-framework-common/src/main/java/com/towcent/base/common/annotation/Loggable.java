package com.towcent.base.common.annotation;

/**
 * Controller方法返回日志注解
 * 
 */
public @interface Loggable {
	
	String value() default "";
}
