package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.Register;
import com.youzan.enable.ddd.command.CommandHub;
import com.youzan.enable.ddd.command.CommandInterceptor;
import com.youzan.enable.ddd.annotation.PostInterceptor;
import com.youzan.enable.ddd.dto.Command;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * PostInterceptorRegister 
 * @author fulan.zjf 2017-11-04
 */
@Component
public class PostInterceptorRegister implements Register, ApplicationContextAware{

    @Resource
    private CommandHub commandHub;
    
    private ApplicationContext applicationContext;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        CommandInterceptor commandInterceptor = (CommandInterceptor) applicationContext.getBean(targetClz);
        PostInterceptor postInterceptorAnn = targetClz.getDeclaredAnnotation(PostInterceptor.class);
        Class<? extends Command>[] supportClasses = postInterceptorAnn.commands();
        registerInterceptor(supportClasses, commandInterceptor);        
    }

    private void registerInterceptor(Class<? extends Command>[] supportClasses, CommandInterceptor commandInterceptor) {
        if (null == supportClasses || supportClasses.length == 0) {
            commandHub.getGlobalPostInterceptors().add(commandInterceptor);
            return;
        } 
        for (Class<? extends Command> supportClass : supportClasses) {
            commandHub.getPostInterceptors().put(supportClass, commandInterceptor);
        }
    }    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
