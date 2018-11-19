package com.towcent.base.common.annotation;

import com.towcent.base.common.enums.DataAccessRuleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户访问权限控制
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataAccessAuth {
	DataAccessRuleEnum[] value() default {};
}
