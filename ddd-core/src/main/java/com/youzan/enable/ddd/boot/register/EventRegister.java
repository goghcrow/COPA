package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.IRegister;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.dto.event.Event;
import com.youzan.enable.ddd.event.EventHub;
import com.youzan.enable.ddd.event.IEventHandler;
import com.youzan.enable.ddd.exception.InfraException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * EventRegister
 *
 * @author shawnzhan.zxy
 * @date 2017/11/20
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class EventRegister implements IRegister, ApplicationContextAware {

    @Resource
    private EventHub eventHub;

    private ApplicationContext applicationContext;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Class<? extends Event> eventClz = getEventFromExecutor(targetClz);
        IEventHandler executor = (IEventHandler) applicationContext.getBean(targetClz);
        eventHub.register(eventClz, executor);
    }

    private Class<? extends Event> getEventFromExecutor(Class<?> eventExecutorClz) {
        Method[] methods = eventExecutorClz.getDeclaredMethods();
        for (Method method : methods) {
            Class<?>[] exeParams = method.getParameterTypes();
            /**
             * This is for return right response type on exception scenarios
             */
            if (CoreConstant.EXE_METHOD.equals(method.getName()) && exeParams.length == 1
                && Event.class.isAssignableFrom(exeParams[0]) && !method.isBridge()) {
                eventHub.getResponseRepository().put(exeParams[0], method.getReturnType());
                return (Class<? extends Event>) exeParams[0];
            }
        }
        throw new InfraException("Event param in " + eventExecutorClz + " " + CoreConstant.EXE_METHOD
                                 + "() is not detected");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
