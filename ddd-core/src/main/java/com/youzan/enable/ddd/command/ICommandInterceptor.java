package com.youzan.enable.ddd.command;

import com.youzan.api.common.response.BaseResult;
import com.youzan.enable.ddd.dto.Command;

/**
 * Interceptor will do AOP processing before or after Command Execution
 * 
 * @author fulan.zjf 2017年10月25日 下午4:04:43
 */
public interface ICommandInterceptor {
   
   /**
    * Pre-processing before command execution
    * @param command
    */
   default void preIntercept(com.youzan.enable.ddd.dto.Command command){}
   
   /**
    * Post-processing after command execution
    * @param command
    * @param response, Note that response could be null, check it before use
    */
   default void postIntercept(Command command, BaseResult response){}

}
