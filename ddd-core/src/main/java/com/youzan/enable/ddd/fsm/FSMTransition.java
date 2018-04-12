package com.youzan.enable.ddd.fsm;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author chuxiaofeng
 */
@SuppressWarnings("WeakerAccess")
@Getter
@Setter
@EqualsAndHashCode(of = {"from", "eventType"})
public final class FSMTransition {

    private String name;
    private FSMState from;
    private FSMState to;

    private Class<? extends FSMEvent> eventType;
    private FSMEventHandler eventHandler;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Transition");
        sb.append("{name='").append(name).append('\'');
        sb.append(", from=").append(from.getName());
        sb.append(", to=").append(to.getName());
        sb.append(", eventType=").append(eventType);
        if (eventHandler != null) {
            sb.append(", eventHandler=").append(eventHandler.getClass().getName());
        }
        sb.append('}');
        return sb.toString();
    }

    public static TransitionBuilder builder() {
        return new TransitionBuilder();
    }
    
    public static class TransitionBuilder {
        private String name;
        private FSMState from;
        private FSMState to;
        private Class<? extends FSMEvent> eventType;
        private FSMEventHandler eventHandler;

        TransitionBuilder() {
        }

        public TransitionBuilder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public TransitionBuilder from(@NotNull FSMState from) {
            this.from = from;
            return this;
        }

        public TransitionBuilder to(@NotNull FSMState to) {
            this.to = to;
            return this;
        }

        public TransitionBuilder on(@NotNull Class<? extends FSMEvent> evt) {
            this.eventType = evt;
            return this;
        }

        public TransitionBuilder on(@NotNull Class<? extends FSMEvent> evt, @Nullable FSMEventHandler evtHandler) {
            this.eventType = evt;
            this.eventHandler = evtHandler;
            return this;
        }

        public FSMTransition build() {
            if (this.name == null) {
                throw new IllegalArgumentException("name must be called");
            }
            if (this.from == null) {
                throw new IllegalArgumentException("from must be called");
            }
            if (this.to == null) {
                throw new IllegalArgumentException("to must be called");
            }
            if (this.eventType == null) {
                throw new IllegalArgumentException("on must be called");
            }
            FSMTransition transition = new FSMTransition();
            transition.name = this.name;
            transition.from = this.from;
            transition.to = this.to;
            transition.eventType = this.eventType;
            transition.eventHandler = this.eventHandler;
            return transition;
        }
    }
}
