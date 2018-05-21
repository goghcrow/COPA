package com.youzan.enable.ddd.boot.register;

import com.youzan.enable.ddd.boot.Register;
import com.youzan.enable.ddd.validator.PlainValidatorRepository;
import com.youzan.enable.ddd.validator.Validator;
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
public class PlainValidatorRegister implements Register, ApplicationContextAware {

    @Resource
    private PlainValidatorRepository plainValidatorRepository;

    private ApplicationContext applicationContext;

    @Override
    public void doRegistration(Class<?> targetClz) {
        Validator plainValidator= (Validator) applicationContext.getBean(targetClz);
        plainValidatorRepository.getPlainValidators().put(plainValidator.getClass(), plainValidator);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
