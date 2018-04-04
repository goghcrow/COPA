package com.youzan.enable.ddd.test.customer.validator.extension;

import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.exception.BizException;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.test.customer.AddCustomerCmd;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.CustomerType;
import com.youzan.enable.ddd.test.customer.validator.extensionpoint.AddCustomerValidatorExtPt;

/**
 * AddCustomerBizOneValidator
 *
 * @author Frank Zhang
 * @date 2018-01-07 1:31 AM
 */
@Extension
public class AddCustomerBizOneValidator implements AddCustomerValidatorExtPt{

    @Override
    public void validate(Object candidate) {
        AddCustomerCmd addCustomerCmd = (AddCustomerCmd) candidate;
        //For BIZ TWO CustomerTYpe could not be VIP
        if(CustomerType.VIP == addCustomerCmd.getCustomerCO().getCustomerType()) {
            throw new BizException("Customer Type could not be VIP for Biz One");
        }
    }

    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return Constants.BIZ_1.equals(bizCode);
    }

}
