package com.youzan.enable.ddd.fsm;

import com.youzan.enable.ddd.exception.CrmException;
import lombok.Getter;

/**
 * @author chuxiaofeng
 */
@Getter
public class FSMException extends CrmException {

    private FSMTransition transition;

    private FSMEvent event;

    private Throwable cause;

    public FSMException(final FSMTransition transition, final FSMEvent event, final Throwable cause) {
        super(cause.getMessage(), cause);

        this.transition = transition;
        this.event = event;
        this.cause = cause;
    }

}
