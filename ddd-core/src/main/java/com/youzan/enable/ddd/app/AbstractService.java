package com.youzan.enable.ddd.app;

import com.youzan.enable.ddd.command.CommandBus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chuxiaofeng
 */
@Service
public abstract class AbstractService {

    @Resource
    protected CommandBus commandBus;

}
