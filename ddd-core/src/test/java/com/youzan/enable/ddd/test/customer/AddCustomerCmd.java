package com.youzan.enable.ddd.test.customer;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * AddCustomerCmd
 *
 * @author Frank Zhang 2018-01-06 7:28 PM
 */
@Data
public class AddCustomerCmd extends BaseCommand {

    @NotNull
    @Valid
    private CustomerDTO customerDTO;
}
