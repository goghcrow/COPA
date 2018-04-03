package com.youzan.enable.ddd.validator;

/**
 * Validator Interface
 * @author fulan.zjf 2017-11-04
 */
public interface IValidator {
    
    /**
     * Validate candidate, throw according exception if failed
     * @param candidate
     */
    void validate(Object candidate);

}
