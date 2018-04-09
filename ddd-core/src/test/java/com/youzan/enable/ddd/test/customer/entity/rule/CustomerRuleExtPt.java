package com.youzan.enable.ddd.test.customer.entity.rule;

import com.youzan.enable.ddd.extension.ExtensionPoint;
import com.youzan.enable.ddd.rule.Rule;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;

/**
 * CustomerRuleExtPt
 *
 * @author Frank Zhang
 * @date 2018-01-07 12:03 PM
 */
public interface CustomerRuleExtPt extends Rule, ExtensionPoint {

    //Different business check for different biz
    boolean addCustomerCheck(CustomerEntity customerEntity);

    //Different upgrade policy for different biz
    default void customerUpgradePolicy(CustomerEntity customerEntity){
        //Nothing special
    }

    /**
     * 子类不使用 Rule 默认 match 方法
     * @param candidate
     * @return
     */
    default boolean check(Object candidate) { return true; }
}
