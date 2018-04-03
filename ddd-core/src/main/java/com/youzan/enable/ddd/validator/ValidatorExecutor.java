package com.youzan.enable.ddd.validator;

import com.youzan.enable.ddd.extension.ExtensionExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ValidatorExecutor
 * 
 * @author fulan.zjf 2017-11-04
 */
@Component
public class ValidatorExecutor extends ExtensionExecutor {

    @Resource
    private PlainValidatorRepository plainValidatorRepository;

    public void validate(Class<? extends IValidator> targetClz, Object candidate) {
        IValidator validator = locateComponent(targetClz);
        validator.validate(candidate);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <C> C locateComponent(Class<C> targetClz) {
        C validator = (C) plainValidatorRepository.getPlainValidators().get(targetClz);
        return null != validator ? validator : super.locateComponent(targetClz);
    }

}