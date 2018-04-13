package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.test.customer.Constants;
import com.youzan.enable.ddd.test.customer.CustomerCO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;
import com.youzan.enable.ddd.test.customer.entity.SourceType;

import javax.annotation.Resource;

/**
 * CustomerBizOneConvertorExt
 *
 * @author Frank Zhang
 * @date 2018-01-07 3:05 AM
 */
@Extension
public class CustomerBizOneConvertorExt  implements CustomerConvertorExtPt{

    @Resource
    private CustomerConvertor customerConvertor;//Composite basic convertor to do basic conversion

    @Override
    public CustomerEntity clientToEntity(CustomerCO customerCO){
        CustomerEntity customerEntity = customerConvertor.dtoToEntity(customerCO);
        //In this business, AD and RFQ are regarded as different source
        if(Constants.SOURCE_AD.equals(customerCO.getSource()))
        {
            customerEntity.setSourceType(SourceType.AD);
        }
        if (Constants.SOURCE_RFQ.equals(customerCO.getSource())){
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
