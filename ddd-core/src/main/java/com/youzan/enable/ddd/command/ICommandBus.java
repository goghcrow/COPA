package com.youzan.enable.ddd.command;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;

/**
 * 
 * CommandBus
 * 
 * @author fulan.zjf 2017年10月21日 下午11:00:58
 */
public interface ICommandBus {

    /**
     * Send command to CommandBus, then the command will be executed by CommandExecutor
     * 
     * @param cmd Command or Query
     * @return Response
     */
    BaseResult send(Command cmd);
}
