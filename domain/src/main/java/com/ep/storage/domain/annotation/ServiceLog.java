package com.ep.storage.domain.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 拦截Service
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLog {
    String description() default "";
}
