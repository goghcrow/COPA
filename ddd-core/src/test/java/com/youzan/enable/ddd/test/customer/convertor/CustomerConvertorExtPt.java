package com.youzan.enable.ddd.test.customer.convertor;

import com.youzan.enable.ddd.convertor.Convertor;
import com.youzan.enable.ddd.extension.ExtensionPoint;
import com.youzan.enable.ddd.test.customer.CustomerDTO;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;

/**
 * CustomerConvertorExtPt
 *
 * @author Frank Zhang
 * @date 2018-01-07 2:37 AM
 */
public interface CustomerConvertorExtPt extends Convertor, ExtensionPoint {

    CustomerEntity dtoToEntity(CustomerDTO customerDTO);

}
