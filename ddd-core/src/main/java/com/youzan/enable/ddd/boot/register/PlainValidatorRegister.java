package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.IRegister;
import com.youzan.enable.ddd.validator.IValidator;
import com.youzan.enable.ddd.validator.PlainValidatorRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Plain Validator is Validator Component without ExtensionPoint
 * @author fulan.zjf
 * @date 2017/12/21
 */
@Component
public class PlainValidatorRegister implements IRegister, ApplicationContextAware {

    @Resource
    private PlainValidatorRepository plainValidatorRepository;

    private ApplicationContext applicationContext;

    @Override
    public void doRegistration(Class<?> targetClz) {
        IValidator plainValidator= (IValidator) applicationContext.getBean(targetClz);
        plainValidatorRepository.getPlainValidators().put(plainValidator.getClass(), plainValidator);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
