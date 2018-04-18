package com.youzan.enable.ddd.command;

import com.google.common.collect.FluentIterable;
import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;
import lombok.Setter;

import java.util.List;

public class CommandInvocation<R extends BaseResult, C extends Command> {
    
    @Setter
    private CommandExecutor<R,C> commandExecutor;
    @Setter
    private Iterable<CommandInterceptor> preInterceptors;
    @Setter
    private Iterable<CommandInterceptor> postInterceptors;
    
    public CommandInvocation() {
        
    }
    
    public CommandInvocation(CommandExecutor<R, C> commandExecutor, List<CommandInterceptor> preInterceptors,
                             List<CommandInterceptor> postInterceptors){
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
        } finally {
            //make sure post interceptors performs even though exception happens
            postIntercept(command, response);     
        }          
        return response;
    }

    private void postIntercept(C command, R response) {
        for (CommandInterceptor postInterceptor : FluentIterable.from(postInterceptors).toSet()) {
            postInterceptor.postIntercept(command, response);
        }
    }

    private void preIntercept(C command) {
        for (CommandInterceptor preInterceptor : FluentIterable.from(preInterceptors).toSet()) {
            preInterceptor.preIntercept(command);
        }
    }
}
