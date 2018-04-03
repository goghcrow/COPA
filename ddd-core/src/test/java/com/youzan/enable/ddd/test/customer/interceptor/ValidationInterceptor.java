package com.youzan.enable.ddd.test.customer.interceptor;

import com.youzan.enable.ddd.command.CommandInterceptor;
import com.youzan.enable.ddd.annotation.PreInterceptor;
import com.youzan.enable.ddd.dto.Command;
import com.youzan.enable.ddd.exception.ParamException;
import com.youzan.enable.ddd.validator.MessageInterpolator;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * ValidationInterceptor
 *
 * @author Frank Zhang 2018-01-06 8:27 PM
 */
@PreInterceptor
public class ValidationInterceptor implements CommandInterceptor {

    //Enable fail fast, which will improve performance
    private ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
            .messageInterpolator(new MessageInterpolator()).buildValidatorFactory();

    @Override
    public void preIntercept(Command command) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Command>> constraintViolations = validator.validate(command);
        constraintViolations.forEach(violation -> {
            throw new ParamException(violation.getPropertyPath() + " " + violation.getMessage());
        });
    }
}