package com.youzan.enable.ddd.test.customer;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.command.Command;
import com.youzan.enable.ddd.command.ICommandExecutor;
import com.youzan.enable.ddd.extension.ExtensionExecutor;
import com.youzan.enable.ddd.test.customer.convertor.CustomerConvertorExtPt;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import com.youzan.enable.ddd.test.customer.validator.extensionpoint.AddCustomerValidatorExtPt;
import com.youzan.enable.ddd.validator.ValidatorExecutor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * AddCustomerCmdExe
 *
 * @author Frank Zhang 2018-01-06 7:48 PM
 */
@Command
@Slf4j
public class AddCustomerCmdExe implements ICommandExecutor<BaseResult, AddCustomerCmd> {
    
    @Resource
    private ValidatorExecutor validatorExecutor;

    @Resource
    private ExtensionExecutor extensionExecutor;


    @Override
    public BaseResult execute(AddCustomerCmd cmd) {
        log.info("Start processing command:" + cmd);
        validatorExecutor.validate(AddCustomerValidatorExtPt.class, cmd);

        //Convert CO to Entity
        CustomerEntity customerEntity = extensionExecutor.execute(CustomerConvertorExtPt.class,
                extension -> extension.clientToEntity(cmd.getCustomerCO()));

        //Call Domain Entity for business logic processing
        log.info("Call Domain Entity for business logic processing..."+customerEntity);
        customerEntity.addNewCustomer();

        log.info("End processing command:" + cmd);
        return new BaseResult();
    }
}
