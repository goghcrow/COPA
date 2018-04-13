package com.youzan.enable.ddd.test.customer.validator.extension;

import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.exception.ParamException;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.test.customer.AddCustomerCmd;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.validator.extensionpoint.AddCustomerValidatorExtPt;

/**
 * AddCustomerBizTwoValidator
 *
 * @author Frank Zhang
 * @date 2018-01-07 1:31 AM
 */
@Extension
public class AddCustomerBizTwoValidator implements AddCustomerValidatorExtPt {

    @Override
    public void validate(Object candidate) {
        AddCustomerCmd addCustomerCmd = (AddCustomerCmd) candidate;
        //For BIZ TWO CustomerTYpe could not be null
        if (addCustomerCmd.getCustomerDTO().getCustomerType() == null)
            throw new ParamException("CustomerType could not be null");
    }

    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return Constants.BIZ_2.equals(bizCode);
    }

}
