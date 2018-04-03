package com.youzan.enable.ddd.test.customer.repository;

import com.youzan.enable.ddd.repository.Repository;
import com.youzan.enable.ddd.test.customer.entity.CustomerEntity;

/**
 * CustomerRepository
 *
 * @author Frank Zhang
 * @date 2018-01-07 11:59 AM
 */
@org.springframework.stereotype.Repository
public class CustomerRepository implements Repository {

    public void persist(CustomerEntity customerEntity){
        System.out.println("Persist customer to DB : "+ customerEntity);
    }
}
