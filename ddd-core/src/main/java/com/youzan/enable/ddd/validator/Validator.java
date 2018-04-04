package com.youzan.enable.ddd.validator;

/**
 * Validator Interface
 * @author fulan.zjf 2017-11-04
 */
@FunctionalInterface
public interface Validator {
    
    /**
     * Validate candidate, throw according exception if failed
     * @param candidate
     */
    void validate(Object candidate);

}
