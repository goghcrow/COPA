package com.youzan.enable.ddd.validator;

import com.youzan.enable.ddd.extension.ExtensionExecutor;
import org.jetbrains.annotations.NotNull;
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

    public void validate(@NotNull Class<? extends Validator> targetClz, Object candidate) {
        Validator validator = locateComponent(targetClz);
        validator.validate(candidate);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <C> C locateComponent(@NotNull Class<C> targetClz) {
        // semantically
        assert targetClz.isInterface();
        C validator = (C) plainValidatorRepository.getPlainValidators().get(targetClz);
        return validator != null ? validator : super.locateComponent(targetClz);
    }

}