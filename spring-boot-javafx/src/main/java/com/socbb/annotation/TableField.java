package com.socbb.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(1)
public @interface TableField {

    String value() default "";
}
