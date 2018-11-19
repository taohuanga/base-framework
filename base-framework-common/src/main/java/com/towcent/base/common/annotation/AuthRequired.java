package com.towcent.base.common.annotation;

import com.towcent.base.common.enums.AuthLevelEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 安全验证
 * 
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {
    AuthLevelEnum value() default AuthLevelEnum.STRICT;
}
