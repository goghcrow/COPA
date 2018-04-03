package com.youzan.enable.ddd.command;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.youzan.enable.ddd.exception.InfraException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command Hub holds all the important information about Command
 * 
 * @author fulan.zjf 2017-10-24
 */
@SuppressWarnings("rawtypes")
@Component
public class CommandHub{

    @Getter
    @Setter
    private ListMultimap<Class/*CommandClz*/, CommandInterceptor> preInterceptors = LinkedListMultimap.create();
    @Getter
    @Setter
    private ListMultimap<Class/*CommandClz*/, CommandInterceptor> postInterceptors = LinkedListMultimap.create();
    @Getter
    @Setter
    private List<CommandInterceptor> globalPreInterceptors = new ArrayList<>(); //全局通用的PreInterceptors
    @Getter
    @Setter
    private List<CommandInterceptor> globalPostInterceptors = new ArrayList<>(); //全局通用的PostInterceptors
    @Getter
    @Setter
    private Map<Class/*CommandClz*/, CommandInvocation> commandRepository = new HashMap<>();
    
    @Getter
    private Map<Class/*CommandClz*/, Class/*ResponseClz*/> responseRepository = new HashMap<>();
    
    public CommandInvocation getCommandInvocation(Class cmdClass) {
        CommandInvocation commandInvocation = commandRepository.get(cmdClass);
        if (commandRepository.get(cmdClass) == null) {
            throw new InfraException(cmdClass + " is not registered in CommandHub, please register first");
        }
        return commandInvocation;
    }
}
