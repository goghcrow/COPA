package com.youzan.enable.ddd.dto.event;

public abstract class NsqEvent extends Event {

    public abstract String getTopic();

}
