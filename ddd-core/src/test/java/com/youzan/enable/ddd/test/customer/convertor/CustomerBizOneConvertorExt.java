package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.CustomerDTO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import com.youzan.enable.ddd.test.customer.entity.SourceType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CustomerBizOneConvertorExt
 *
 * @author Frank Zhang
 * @date 2018-01-07 3:05 AM
 */
@Extension @Component
public class CustomerBizOneConvertorExt  implements CustomerConvertorExtPt{

    @Resource
    private CustomerConvertor customerConvertor;//Composite basic convertor to do basic conversion

    @Override
    public CustomerEntity dtoToEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntity = customerConvertor.dtoToEntity(customerDTO);
        //In this business, AD and RFQ are regarded as different source
        if(Constants.SOURCE_AD.equals(customerDTO.getSource()))
        {
            customerEntity.setSourceType(SourceType.AD);
        }
        if (Constants.SOURCE_RFQ.equals(customerDTO.getSource())){
            customerEntity.setSourceType(SourceType.RFQ);
        }
        return customerEntity;
    }

    @Override
    public boolean match() {
        String bizCode = Context.get("bizCode");
        return Constants.BIZ_1.equals(bizCode);
    }
}
