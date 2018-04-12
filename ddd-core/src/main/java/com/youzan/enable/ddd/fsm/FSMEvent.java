package com.youzan.enable.ddd.fsm;

import lombok.Getter;
import lombok.ToString;

/**
 * @author chuxiaofeng
 */
@Getter
@ToString
public abstract class FSMEvent {

    @Getter
    protected String name;

    protected FSMEvent(final String name) {
        this.name = name;
    }
}
