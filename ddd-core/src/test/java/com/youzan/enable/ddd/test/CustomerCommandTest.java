package com.youzan.enable.ddd.test;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.TestConfig;
import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.exception.BasicErrorCode;
import com.youzan.enable.ddd.test.customer.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * CustomerCommandTest
 *
 * @author Frank Zhang 2018-01-06 7:55 PM
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CustomerCommandTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Resource
    private CustomerService customerService;

    @Value("${bizCode}")
    private String bizCode;

    @Before
    public void setUp() {
        Context.set("bizCode", bizCode);
        Context.set("tenantId", Constants.TENANT_ID);
    }

    @Test
    public void testBizOneAddCustomerSuccess(){
        //1. Prepare
        Context.set("bizCode", Constants.BIZ_1);
        Context.set("tenantId", Constants.TENANT_ID);


        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCompanyName("alibaba");
        customerDTO.setSource(Constants.SOURCE_RFQ);
        customerDTO.setCustomerType(CustomerType.IMPORTANT);
        addCustomerCmd.setCustomerDTO(customerDTO);

        //2. Execute
        BaseResult response = customerService.addCustomer(addCustomerCmd);

        //3. Expect Success
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testBizOneAddCustomerFailure(){
        //1. Prepare
        Context.set("bizCode", Constants.BIZ_1);
        Context.set("tenantId", Constants.TENANT_ID);

        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCompanyName("alibaba");
        customerDTO.setSource(Constants.SOURCE_AD);
        customerDTO.setCustomerType(CustomerType.IMPORTANT);
        addCustomerCmd.setCustomerDTO(customerDTO);

        //2. Execute
        BaseResult response = customerService.addCustomer(addCustomerCmd);

        //3. Expect exception
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(response.getCode(), BasicErrorCode.BIZ_ERROR.getCode());
    }

    @Test
    public void testBizTwoAddCustomer(){
        //1. Prepare
        Context.set("bizCode", Constants.BIZ_2);
        Context.set("tenantId", Constants.TENANT_ID);

        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCompanyName("alibaba");
        customerDTO.setSource(Constants.SOURCE_AD);
        customerDTO.setCustomerType(CustomerType.IMPORTANT);
        addCustomerCmd.setCustomerDTO(customerDTO);

        //2. Execute
        BaseResult response = customerService.addCustomer(addCustomerCmd);

        //3. Expect Success
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testCompanyTypeViolation(){
        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCompanyName("alibaba");
        customerDTO.setSource("p4p");
        customerDTO.setCustomerType(CustomerType.VIP);
        addCustomerCmd.setCustomerDTO(customerDTO);
        BaseResult response = customerService.addCustomer(addCustomerCmd);

        //Expect biz exception
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(response.getCode(), BasicErrorCode.BIZ_ERROR.getCode());
    }

    @Test
    public void testParamValidationFail(){
        AddCustomerCmd addCustomerCmd = new AddCustomerCmd();
        BaseResult response = customerService.addCustomer(addCustomerCmd);

        //Expect parameter validation error
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(response.getCode(), BasicErrorCode.PARAM_ERROR.getCode());
    }
}
