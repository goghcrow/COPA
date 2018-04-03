package com.youzan.enable.ddd.test;

import com.youzan.enable.ddd.TestConfig;
import com.youzan.enable.ddd.boot.Bootstrap;
import com.youzan.enable.ddd.boot.RegisterFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringConfigTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        Bootstrap bootstrap = (Bootstrap)context.getBean("bootstrap");
        RegisterFactory registerFactory = (RegisterFactory)context.getBean("registerFactory");
        System.out.println(registerFactory);
        System.out.println(bootstrap.getPackages());
    }
}
