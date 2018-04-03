package com.youzan.enable.ddd.boot;

import com.youzan.enable.ddd.boot.register.*;
import com.youzan.enable.ddd.annotation.Command;
import com.youzan.enable.ddd.annotation.PostInterceptor;
import com.youzan.enable.ddd.annotation.PreInterceptor;
import com.youzan.enable.ddd.common.CoreConstant;
import com.youzan.enable.ddd.annotation.Event;
import com.youzan.enable.ddd.exception.InfraException;
import com.youzan.enable.ddd.annotation.Extension;
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

    public Register getRegister(Class<?> targetClz) {
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
        Event eventHandlerAnn = targetClz.getDeclaredAnnotation(Event.class);
        if (eventHandlerAnn != null) {
            return eventRegister;
        }
        if (isPlainValidator(targetClz)) {
            return plainValidatorRegister;
        }
        if (isPlainRule(targetClz)) {
            return plainRuleRegister;
        }
        return null;
    }

    private boolean isPlainRule(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.RULE_CLASS) && makeSureItsNotExtensionPoint(targetClz)) {
            return true;
        }
        return false;
    }

    private boolean isPlainValidator(Class<?> targetClz) {
        if (ClassInterfaceChecker.check(targetClz, CoreConstant.VALIDATOR_CLASS) && makeSureItsNotExtensionPoint(targetClz)) {
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
