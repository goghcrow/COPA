package com.youzan.enable.ddd.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Extension 
 * @author fulan.zjf 2017-11-05
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Extension {
    int order() default 0;
}
