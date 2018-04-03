package com.youzan.enable.ddd.boot.register;

import com.google.common.collect.Iterables;
import com.youzan.enable.ddd.boot.IRegister;
import com.youzan.enable.ddd.command.CommandHub;
import com.youzan.enable.ddd.command.CommandInvocation;
import com.youzan.enable.ddd.command.ICommandExecutor;
import com.youzan.enable.ddd.command.ICommandInterceptor;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.dto.Command;
import com.youzan.enable.ddd.exception.InfraException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * CommandRegister
 * 
 * @author fulan.zjf 2017-11-04
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class CommandRegister implements IRegister, ApplicationContextAware {

    @Resource
    private CommandHub         commandHub;

    private ApplicationContext applicationContext;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Class<? extends Command> commandClz = getCommandFromExecutor(targetClz);
        CommandInvocation commandInvocation = new CommandInvocation();
        commandInvocation.setCommandExecutor((ICommandExecutor) applicationContext.getBean(targetClz));
        commandInvocation.setPreInterceptors(collectInterceptors(commandClz, true));
        commandInvocation.setPostInterceptors(collectInterceptors(commandClz, false));
        commandHub.getCommandRepository().put(commandClz, commandInvocation);
    }

    private Class<? extends Command> getCommandFromExecutor(Class<?> commandExecutorClz) {
        Method[] methods = commandExecutorClz.getDeclaredMethods();
        for (Method method : methods) {
            Class<?>[] exeParams = method.getParameterTypes();
            /**
             * This is for return right response type on exception scenarios
             */
            if (CoreConstant.EXE_METHOD.equals(method.getName()) && exeParams.length == 1
                && Command.class.isAssignableFrom(exeParams[0]) && !method.isBridge()) {
                commandHub.getResponseRepository().put(exeParams[0], method.getReturnType());
                return (Class<? extends Command>) exeParams[0];
            }
        }
        throw new InfraException("Command param in " + commandExecutorClz + " " + CoreConstant.EXE_METHOD
                                 + "() is not detected");
    }

    private Iterable<ICommandInterceptor> collectInterceptors(Class<? extends Command> commandClass, boolean pre) {
        /**
         * add 通用的Interceptors
         */
        Iterable<ICommandInterceptor> commandItr = Iterables.concat((pre ? commandHub.getGlobalPreInterceptors() : commandHub.getGlobalPostInterceptors()));
        /**
         * add command自己专属的Interceptors
         */
        Iterables.concat(commandItr, (pre ? commandHub.getPreInterceptors() : commandHub.getPostInterceptors()).get(commandClass));
        /**
         * add parents的Interceptors
         */
        Class<?> superClass = commandClass.getSuperclass();
        while (Command.class.isAssignableFrom(superClass)) {
            Iterables.concat(commandItr, (pre ? commandHub.getPreInterceptors() : commandHub.getPostInterceptors()).get(commandClass));
            superClass = superClass.getSuperclass();
        }
        return commandItr;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
