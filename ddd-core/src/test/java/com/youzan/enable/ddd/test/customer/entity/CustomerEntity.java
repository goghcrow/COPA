package com.youzan.enable.ddd.test.customer.entity;

import com.youzan.enable.ddd.domain.Entity;
import com.youzan.enable.ddd.extension.ExtensionExecutor;
import com.youzan.enable.ddd.test.customer.CustomerType;
import com.youzan.enable.ddd.test.customer.entity.rule.CustomerRuleExtPt;
import com.youzan.enable.ddd.test.customer.repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Customer Entity
 *
 * @author Frank Zhang
 * @date 2018-01-07 2:38 AM
 */
@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerEntity extends Entity<String> {

    private String companyName;
    private SourceType sourceType;
    private CustomerType customerType;

    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private ExtensionExecutor extensionExecutor;

    public CustomerEntity() {

    }

    public void addNewCustomer() {
        //Add customer policy
        extensionExecutor.execute(CustomerRuleExtPt.class, extension -> extension.addCustomerCheck(this));

        //Persist customer
        customerRepository.persist(this);
    }
}
