package com.youzan.enable.ddd.boot.register;

import com.google.common.collect.Iterables;
import com.youzan.enable.ddd.boot.Register;
import com.youzan.enable.ddd.command.CommandExecutor;
import com.youzan.enable.ddd.command.CommandHub;
import com.youzan.enable.ddd.command.CommandInterceptor;
import com.youzan.enable.ddd.command.CommandInvocation;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.dto.Command;
import com.youzan.enable.ddd.exception.InfraException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * CommandRegister
 * 
 * @author fulan.zjf 2017-11-04
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class CommandRegister implements Register, ApplicationContextAware {

    @Resource
    private CommandHub         commandHub;

    private ApplicationContext applicationContext;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Class<? extends Command> commandClz = getCommandFromExecutor(targetClz);

        CommandInvocation commandInvocation = new CommandInvocation();
        commandInvocation.setCommandExecutor((CommandExecutor) applicationContext.getBean(targetClz));
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

    private Iterable<CommandInterceptor> collectInterceptors(Class<? extends Command> commandClass, boolean pre) {
        /**
         * add 通用的Interceptors
         * 这里将 commandInvocation 的拦截器迭代器指向 commandHub 的全局迭代器, 引用关系
         * 这用就不用处理 扫描 command 与 Interceptors 的先后关系
         */
        Iterable<CommandInterceptor> commandItr = Iterables.concat((pre ? commandHub.getGlobalPreInterceptors() : commandHub.getGlobalPostInterceptors()));

        /**
         * add command自己专属的Interceptors
         * fixBug: 合并专属 Interceptors
         */
        List<CommandInterceptor> selfInterceptors = (pre ? commandHub.getPreInterceptors() : commandHub.getPostInterceptors()).get(commandClass);
        commandItr = Iterables.concat(commandItr, selfInterceptors);

        /**
         * add parents的Interceptors
         * fixBug: 1. 合并parents Interceptors
         *         2. get(commandClass) -->  get(superClass)
         */
        Class<?> superClass = commandClass.getSuperclass();
        while (Command.class.isAssignableFrom(superClass)) {
            List<CommandInterceptor> parentInterceptors = (pre ? commandHub.getPreInterceptors() : commandHub.getPostInterceptors()).get(superClass);
            commandItr = Iterables.concat(commandItr, parentInterceptors);
            superClass = superClass.getSuperclass();
        }

        return commandItr;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
