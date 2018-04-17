package com.youzan.enable.ddd.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextHelper
 *
 * @author Frank Zhang
 * @date 2018-01-07 12:30 PM
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(@NotNull Class<T> requiredType){
        return ApplicationContextHelper.applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return ApplicationContextHelper.applicationContext.getBean(requiredType, args);
    }

    public static Object getBean(@NotNull String name, Object... args) {
        return ApplicationContextHelper.applicationContext.getBean(name, args);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
