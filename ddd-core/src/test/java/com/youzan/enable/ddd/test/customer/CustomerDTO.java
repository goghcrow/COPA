package com.youzan.enable.ddd.test.customer;

import com.youzan.enable.ddd.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerDTO extends DTO {

    @NotEmpty
    private String companyName;
    @NotEmpty
    private String source;  //advertisement, p4p, RFQ, ATM
    private CustomerType customerType; //potential, intentional, important, vip

}
