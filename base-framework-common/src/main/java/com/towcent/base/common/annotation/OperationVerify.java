package com.towcent.base.common.annotation;

import com.towcent.base.common.enums.OperationVerifyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块验证
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationVerify {
	
	// 不再用枚举类型表示操作
	OperationVerifyEnum value() default OperationVerifyEnum.NONE;

	String operation() default "";
}
