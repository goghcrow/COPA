package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.IRegister;
import com.youzan.enable.ddd.command.CommandHub;
import com.youzan.enable.ddd.command.ICommandInterceptor;
import com.youzan.enable.ddd.command.PreInterceptor;
import com.youzan.enable.ddd.dto.Command;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * PreInterceptorRegister 
 * @author fulan.zjf 2017-11-04
 */
@Component
public class PreInterceptorRegister implements IRegister, ApplicationContextAware{

    @Resource
    private CommandHub commandHub;
    
    private ApplicationContext applicationContext;
    
    @Override
    public void doRegistration(Class<?> targetClz) {
        ICommandInterceptor commandInterceptor = (ICommandInterceptor) applicationContext.getBean(targetClz);
        PreInterceptor preInterceptorAnn = targetClz.getDeclaredAnnotation(PreInterceptor.class);
        Class<? extends Command>[] supportClasses = preInterceptorAnn.commands();
        registerInterceptor(supportClasses, commandInterceptor);        
    }

    private void registerInterceptor(Class<? extends Command>[] supportClasses, ICommandInterceptor commandInterceptor) {
        if (null == supportClasses || supportClasses.length == 0) {
            commandHub.getGlobalPreInterceptors().add(commandInterceptor);
            return;
        } 
        for (Class<? extends Command> supportClass : supportClasses) {
            commandHub.getPreInterceptors().put(supportClass, commandInterceptor);
        }
    }    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
