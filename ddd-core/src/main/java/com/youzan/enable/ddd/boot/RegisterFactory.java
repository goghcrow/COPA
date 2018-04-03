package com.youzan.enable.ddd.boot;

import com.youzan.enable.ddd.boot.register.*;
import com.youzan.enable.ddd.command.Command;
import com.youzan.enable.ddd.command.PostInterceptor;
import com.youzan.enable.ddd.command.PreInterceptor;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.event.EventHandler;
import com.youzan.enable.ddd.exception.InfraException;
import com.youzan.enable.ddd.extension.Extension;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RegisterFactory
 *
 * @author fulan.zjf 2017-11-04
 */
@Component
public class RegisterFactory{

    @Resource
    private PreInterceptorRegister preInterceptorRegister;
    @Resource
    private PostInterceptorRegister postInterceptorRegister;
    @Resource
    private CommandRegister commandRegister;
    @Resource
    private ExtensionRegister extensionRegister;
    @Resource
    private EventRegister eventRegister;
    @Resource
    private PlainValidatorRegister plainValidatorRegister;
    @Resource
    private PlainRuleRegister plainRuleRegister;

    public IRegister getRegister(Class<?> targetClz) {
        PreInterceptor preInterceptorAnn = targetClz.getDeclaredAnnotation(PreInterceptor.class);
        if (preInterceptorAnn != null) {
            return preInterceptorRegister;
        }
        PostInterceptor postInterceptorAnn = targetClz.getDeclaredAnnotation(PostInterceptor.class);
        if (postInterceptorAnn != null) {
            return postInterceptorRegister;
        }
        Command commandAnn = targetClz.getDeclaredAnnotation(Command.class);
        if (commandAnn != null) {
            return commandRegister;
        }
        Extension extensionAnn = targetClz.getDeclaredAnnotation(Extension.class);
        if (extensionAnn != null) {
            return extensionRegister;
        }
        if (isPlainValidator(targetClz)) {
            return plainValidatorRegister;
        }
        if (isPlainRule(targetClz)) {
            return plainRuleRegister;
        }
        EventHandler eventHandlerAnn = targetClz.getDeclaredAnnotation(EventHandler.class);
        if (eventHandlerAnn != null) {
            return eventRegister;
        }
        return null;
    }

    private boolean isPlainRule(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.IRULE_CLASS) && makeSureItsNotExtensionPoint(targetClz)) {
            return true;
        }
        return false;
    }

    private boolean isPlainValidator(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.IVALIDATOR_CLASS) && makeSureItsNotExtensionPoint(targetClz)) {
            return true;
        }
        return false;
    }

    private boolean makeSureItsNotExtensionPoint(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.EXTENSIONPOINT_CLASS)) {
            throw new InfraException(
                "Please add @Extension for " + targetClz.getSimpleName() + " since it's a ExtensionPoint");
        }
        return true;
    }
}
