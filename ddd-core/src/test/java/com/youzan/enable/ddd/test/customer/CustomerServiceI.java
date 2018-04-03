package com.youzan.enable.ddd.test.customer;

import com.youzan.api.common.response.BaseResult;
import com.youzan.api.common.response.PlainResult;

/**
 * CustomerServiceI
 *
 * @author Frank Zhang 2018-01-06 7:24 PM
 */
public interface CustomerServiceI {
    BaseResult addCustomer(AddCustomerCmd addCustomerCmd);
    PlainResult<CustomerCO> getCustomer(GetOneCustomerQry getOneCustomerQry);
}
