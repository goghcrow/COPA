package com.youzan.enable.ddd.test.customer;

import com.youzan.api.common.response.BaseResult;
import com.youzan.api.common.response.PlainResult;
import com.youzan.enable.ddd.command.ICommandBus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * CustomerServiceImpl
 *
 * @author Frank Zhang 2018-01-06 7:40 PM
 */
@Service
public class CustomerServiceImpl implements CustomerServiceI{

    @Resource
    private ICommandBus commandBus;

    @Override
    public BaseResult addCustomer(AddCustomerCmd addCustomerCmd) {
        return commandBus.send(addCustomerCmd);
    }

    @Override
    public PlainResult<CustomerCO> getCustomer(GetOneCustomerQry getOneCustomerQry) {
        return null;
    }
}
