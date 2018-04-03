package com.youzan.enable.ddd.test.customer.interceptor;

import com.youzan.enable.ddd.command.CommandInterceptor;
import com.youzan.enable.ddd.annotation.PreInterceptor;
import com.youzan.enable.ddd.dto.Command;

/**
 * ContextInterceptor
 *
 * @author Frank Zhang
 * @date 2018-01-07 1:21 AM
 */
@PreInterceptor
public class ContextInterceptor  implements CommandInterceptor {

    @Override
    public void preIntercept(Command command) {
        // TenantContext.set(Constants.TENANT_ID, Constants.BIZ_1);
    }
}
