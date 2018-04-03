package com.youzan.enable.ddd.command;

import com.google.common.collect.FluentIterable;
import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;
import lombok.Setter;

import java.util.List;

public class CommandInvocation<R extends BaseResult, C extends Command> {
    
    @Setter
    private ICommandExecutor<R,C> commandExecutor;
    @Setter
    private Iterable<ICommandInterceptor> preInterceptors;
    @Setter
    private Iterable<ICommandInterceptor> postInterceptors;
    
    public CommandInvocation() {
        
    }
    
    public CommandInvocation(ICommandExecutor<R, C> commandExecutor, List<ICommandInterceptor> preInterceptors,
                             List<ICommandInterceptor> postInterceptors){
        this.commandExecutor = commandExecutor;
        this.preInterceptors = preInterceptors;
        this.postInterceptors = postInterceptors;
    }

    public R invoke(C command) {
        preIntercept(command);
        R response = null;
        try {
            response = commandExecutor.execute(command);  
            response.setSuccess(true);
        }
        finally {
            //make sure post interceptors performs even though exception happens
            postIntercept(command, response);     
        }          
        return response;
    }

    private void postIntercept(C command, R response) {
        for (ICommandInterceptor postInterceptor : FluentIterable.from(postInterceptors).toSet()) {
            postInterceptor.postIntercept(command, response);
        }
    }

    private void preIntercept(C command) {
        for (ICommandInterceptor preInterceptor : FluentIterable.from(preInterceptors).toSet()) {
            preInterceptor.preIntercept(command);
        }
    }
}
