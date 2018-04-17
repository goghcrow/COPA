package com.youzan.enable.ddd.test.customer.entity.rule;

import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import org.springframework.stereotype.Component;

/**
 * CustomerBizTwoRuleExt
 *
 * @author Frank Zhang
 * @date 2018-01-07 12:10 PM
 */
@Extension @Component
public class CustomerBizTwoRuleExt implements CustomerRuleExtPt {

    @Override
    public boolean addCustomerCheck(CustomerEntity customerEntity) {
        //Any Customer can be added
        return true;
    }

    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return Constants.BIZ_2.equals(bizCode);
    }

}
