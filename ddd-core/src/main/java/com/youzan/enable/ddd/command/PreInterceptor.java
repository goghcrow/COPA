package com.youzan.enable.ddd.command;

import com.youzan.enable.ddd.dto.Command;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreInterceptor {

    Class<? extends Command>[] commands() default {};

}
