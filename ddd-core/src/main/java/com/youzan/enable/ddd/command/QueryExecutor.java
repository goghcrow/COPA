package com.youzan.enable.ddd.command;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;

public interface QueryExecutor<R extends BaseResult, C extends Command>
        extends CommandExecutor<R,C> {
}
