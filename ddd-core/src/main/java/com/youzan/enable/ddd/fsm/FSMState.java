package com.youzan.enable.ddd.fsm;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author chuxiaofeng
 */
@EqualsAndHashCode
public class FSMState {

    @Getter
    private String name;

    public FSMState(final String name) {
        this.name = name;
    }

}
