package com.youzan.enable.ddd.dto.event;

/**
 * @author chuxiaofeng
 */
public abstract class NsqEvent extends Event {

    public abstract String getTopic();

}
