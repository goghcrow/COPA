package com.youzan.enable.ddd.command;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;

public interface IQueryExecutor<R extends BaseResult, C extends Command>
        extends ICommandExecutor<R,C> {
}
