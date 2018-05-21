package com.youzan.enable.ddd.test.customer.interceptor;

import com.youzan.enable.ddd.annotation.PreInterceptor;
import com.youzan.enable.ddd.command.CommandInterceptor;
import com.youzan.enable.ddd.dto.Command;
import com.youzan.enable.ddd.test.customer.BaseCommand;

/**
 * ContextInterceptor
 *
 * @author Frank Zhang
 * @date 2018-01-07 1:21 AM
 */
@PreInterceptor(commands = { BaseCommand.class })
public class TestInterceptor implements CommandInterceptor {

    @Override
    public void preIntercept(Command command) {
        System.out.println("测试command 父类 interceptor");
    }
}
