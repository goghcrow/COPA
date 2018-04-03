package com.youzan.enable.ddd.command;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;

/**
 * 
 * CommandExecutor
 * 
 * @author fulan.zjf 2017年10月21日 下午11:01:05
 */
public interface CommandExecutor<R extends BaseResult, C extends Command> {

    R execute(C cmd);
}
