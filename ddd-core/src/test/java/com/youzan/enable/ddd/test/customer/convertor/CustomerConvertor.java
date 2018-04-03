package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.common.ApplicationContextHelper;
import com.youzan.enable.ddd.convertor.Convertor;
import com.youzan.enable.ddd.test.customer.CustomerCO;
import com.youzan.enable.ddd.test.customer.CustomerDO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import org.springframework.stereotype.Component;

/**
 * CustomerConvertor
 *
 * @author Frank Zhang
 * @date 2018-01-07 3:08 AM
 */
@Component
public class CustomerConvertor implements Convertor<CustomerCO, CustomerEntity, CustomerDO> {

    @Override
    public CustomerEntity clientToEntity(CustomerCO customerCO){
        CustomerEntity customerEntity = (CustomerEntity) ApplicationContextHelper.getBean(CustomerEntity.class);
        customerEntity.setCompanyName(customerCO.getCompanyName());
        customerEntity.setCustomerType(customerCO.getCustomerType());
        return customerEntity;
    }
}
