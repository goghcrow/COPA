package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.common.ApplicationContextHelper;
import com.youzan.enable.ddd.convertor.Convertor;
import com.youzan.enable.ddd.test.customer.CustomerDO;
import com.youzan.enable.ddd.test.customer.CustomerDTO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import org.springframework.stereotype.Component;

/**
 * CustomerConvertor
 *
 * @author Frank Zhang
 * @date 2018-01-07 3:08 AM
 */
@Component
public class CustomerConvertor implements Convertor<CustomerDTO, CustomerEntity, CustomerDO> {

    @Override
    public CustomerEntity dtoToEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntity = (CustomerEntity) ApplicationContextHelper.getBean(CustomerEntity.class);
        customerEntity.setCompanyName(customerDTO.getCompanyName());
        customerEntity.setCustomerType(customerDTO.getCustomerType());
        return customerEntity;
    }
}
