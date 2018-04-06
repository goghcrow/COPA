package com.youzan.enable.ddd.common;

import com.youzan.enable.ddd.extension.ExtensionPoint;
import com.youzan.enable.ddd.rule.Rule;
import com.youzan.enable.ddd.validator.Validator;

/**
 * CoreConstant 
 * @author fulan.zjf 2017-11-04
 */
public class CoreConstant {

    /**
     * 扩展点接口必须以 ExtPt 结尾
     */
    public final static String EXTENSION_EXTPT_NAMING = "ExtPt";
    public final static String VALIDATOR_NAMING = "Validator";
    public final static String RULE_NAMING = "Rule";
    
    
    public final static String EXTENSIONPOINT_CLASS = "ExtensionPoint";
    public final static String VALIDATOR_CLASS = "Validator";
    public final static String RULE_CLASS = "Rule";

    public final static String EXE_METHOD = "execute";

}
