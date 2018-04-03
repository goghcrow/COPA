package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.convertor.IConvertor;
import com.youzan.enable.ddd.extension.IExtensionPoint;
import com.youzan.enable.ddd.test.customer.CustomerCO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;

/**
 * CustomerConvertorExtPt
 *
 * @author Frank Zhang
 * @date 2018-01-07 2:37 AM
 */
public interface CustomerConvertorExtPt extends IConvertor, IExtensionPoint {

    CustomerEntity clientToEntity(CustomerCO customerCO);

}
