package com.youzan.enable.ddd.fsm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author chuxiaofeng
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
@Getter
@Slf4j
public final class FSM {

    private FSMState currentState;

    private FSMState initialState;
    private Set<FSMState> finalStates;

    private Set<FSMState> states;

    private Set<FSMTransition> transitions;

    private FSM(final Set<FSMState> states, final FSMState initialState) {
        this.states = states;
        this.initialState = initialState;
        currentState = initialState;

        transitions = new HashSet<>();
        finalStates = new HashSet<>();
    }

    public final List<FSMTransition> getReachableTransitions(@NotNull FSMState state) {
        Objects.requireNonNull(state);

        if (!finalStates.isEmpty() && finalStates.contains(currentState)) {
            return new ArrayList<>(0);
        }

        List<FSMTransition> reachable = new ArrayList<>();
        for (FSMTransition transition : transitions) {
            if (transition.getFrom().equals(state)) {
                reachable.add(transition);
            }
        }
        return reachable;
    }

    /**
     * 触发事件返回当前状态
     * @param event
     * @return
     * @throws FSMException
     */
    public final synchronized FSMState fire(@NotNull final FSMEvent event) throws FSMException {
        Objects.requireNonNull(event);

        if (!finalStates.isEmpty() && finalStates.contains(currentState)) {
            throw new IllegalArgumentException(
                    "FSM is in final state '" + currentState.getName() + "', event " + event + " is ignored.");
        }

        for (FSMTransition transition : transitions) {

            boolean legalToState = states.contains(transition.getTo());

            boolean matchedTransition = currentState.equals(transition.getFrom()) &&
                    transition.getEventType().equals(event.getClass());

            if (matchedTransition && legalToState) {
                try {
                    if (transition.getEventHandler() != null) {
                        transition.getEventHandler().handle(event);
                    }
                    currentState = transition.getTo();
                    return currentState;
                } catch (Exception e) {
                    log.error("An exception occurred during handling event " + event + " of transition " + transition, e);
                    throw new FSMException(transition, event, e);
                }
            }
        }

        throw new IllegalStateException("Invalid event: " + event);
    }


    public static FSMBuilder builder(@NotNull final Set<FSMState> states, @NotNull final FSMState initialState) {
        return new FSMBuilder(states, initialState);
    }

    public static class FSMBuilder {

        private FSM fsm;

        public FSMBuilder(final Set<FSMState> states, final FSMState initialState) {
            fsm = new FSM(states, initialState);
        }

        public FSMBuilder transition(final FSMTransition...transitions) {
            for (FSMTransition transition : transitions) {
                validateTransitionDef(transition, fsm);
                fsm.transitions.add(transition);
            }
            return this;
        }

        public FSMBuilder finalState(final FSMState...finalStates) {
            fsm.finalStates.addAll(Arrays.asList(finalStates));
            return this;
        }

        public FSM build() {
            validateFsmDef(fsm);
            return fsm;
        }

        private void validateFsmDef(FSM fsm) {
            Set<FSMState> states = fsm.getStates();
            FSMState initialState = fsm.getInitialState();

            if (!states.contains(initialState)) {
                throw new IllegalStateException("Initial state '" + initialState.getName() + "' must belong to FSM states: " +
                        dumpFSMStates(states));
            }

            for (FSMState finalState : fsm.getFinalStates()) {
                if (!states.contains(finalState)) {
                    throw new IllegalStateException("Final state '" + finalState.getName() + "' must belong to FSM states: " +
                            dumpFSMStates(states));
                }
            }
        }

        private void validateTransitionDef(final FSMTransition transition, @NotNull FSM fsm) {

            String transitionName = transition.getName();
            FSMState sourceState = transition.getFrom();
            FSMState targetState = transition.getTo();

            if (sourceState == null) {
                throw new IllegalArgumentException("No source state is defined for transition '" + transitionName + "'");
            }
            if (targetState == null) {
                throw new IllegalArgumentException("No target state is defined for transition '" + transitionName + "'");
            }
            if (transition.getEventType() == null) {
                throw new IllegalArgumentException("No event type is defined for transition '" + transitionName + "'");
            }
            if (!fsm.getStates().contains(sourceState)) {
                throw new IllegalArgumentException("Source state '" + sourceState.getName() + "' is not registered in FSM states for transition '" + transitionName + "'");
            }
            if (!fsm.getStates().contains(targetState)) {
                throw new IllegalArgumentException("target state '" + targetState.getName() + "' is not registered in FSM states for transition '" + transitionName + "'");
            }
        }

        private static String dumpFSMStates(final Set<FSMState> states) {
            StringBuilder result = new StringBuilder();
            for (FSMState state : states) {
                result.append(state.getName()).append(";");
            }
            return result.toString();
        }
    }
}
