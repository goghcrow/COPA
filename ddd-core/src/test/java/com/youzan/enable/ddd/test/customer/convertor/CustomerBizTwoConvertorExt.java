package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.CustomerDTO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import com.youzan.enable.ddd.test.customer.entity.SourceType;

import javax.annotation.Resource;

/**
 * CustomerBizTwoConvertorExt
 *
 * @author Frank Zhang
 * @date 2018-01-07 3:05 AM
 */
@Extension
public class CustomerBizTwoConvertorExt implements CustomerConvertorExtPt{

    @Resource
    private CustomerConvertor customerConvertor;//Composite basic convertor to do basic conversion

    @Override
    public CustomerEntity dtoToEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntity = customerConvertor.dtoToEntity(customerDTO);
        //In this business, if customers from RFQ and Advertisement are both regarded as Advertisement
        if(Constants.SOURCE_AD.equals(customerDTO.getSource()) || Constants.SOURCE_RFQ.equals(customerDTO.getSource()))
        {
            customerEntity.setSourceType(SourceType.AD);
        }
        return customerEntity;
    }

    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return Constants.BIZ_2.equals(bizCode);
    }
}
