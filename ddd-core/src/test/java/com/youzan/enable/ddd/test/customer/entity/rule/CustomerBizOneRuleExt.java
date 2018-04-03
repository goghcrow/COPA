package com.youzan.enable.ddd.test.customer.entity.rule;

import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.exception.BizException;
import com.youzan.enable.ddd.extension.Extension;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import com.youzan.enable.ddd.test.customer.entity.SourceType;

/**
 * CustomerBizOneRuleExt
 *
 * @author Frank Zhang
 * @date 2018-01-07 12:10 PM
 */
@Extension
public class CustomerBizOneRuleExt implements CustomerRuleExtPt{

    @Override
    public boolean addCustomerCheck(CustomerEntity customerEntity) {
        if(SourceType.AD == customerEntity.getSourceType()){
            throw new BizException("Sorry, Customer from advertisement can not be added in this period");
        }
        return true;
    }

    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return Constants.BIZ_1.equals(bizCode);
    }
}
