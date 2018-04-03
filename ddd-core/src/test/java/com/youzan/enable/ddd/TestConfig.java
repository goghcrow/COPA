package com.youzan.enable.ddd;

import com.youzan.enable.ddd.boot.Bootstrap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * TestConfig
 *
 * @author Frank Zhang 2018-01-06 7:57 AM
 */
@Configuration
@ComponentScan("com.youzan.enable")
@PropertySource(value = {"/sample.properties"})
public class TestConfig {

    @Bean(initMethod = "init")
    public Bootstrap bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        List<String> packagesToScan  = new ArrayList<>();
        packagesToScan.add("com.youzan.enable.ddd.test");
        bootstrap.setPackages(packagesToScan);
        return bootstrap;
    }
}
