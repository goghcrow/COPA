package com.youzan.enable.ddd.fsm;


/**
 * @author chuxiaofeng
 */
@FunctionalInterface
public interface FSMEventHandler {

    void handle(FSMEvent event) throws Exception;

}
