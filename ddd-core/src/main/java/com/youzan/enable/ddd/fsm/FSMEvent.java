package com.youzan.enable.ddd.fsm;

import lombok.Getter;

/**
 * @author chuxiaofeng
 */
@Getter
public abstract class FSMEvent {

    @Getter
    protected String name;

    protected FSMEvent(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FSMEvent(" + name + ")";
    }
}
