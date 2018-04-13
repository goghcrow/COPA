package com.youzan.enable.ddd.test;

import com.youzan.enable.ddd.fsm.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@RunWith(JUnit4.class)
public class FSMTest {

    @Test
    public void testTicketFlow() {

        class CreatedToOpenEvt extends FSMEvent {
            protected CreatedToOpenEvt() {
                super("CreatedToOpenEvt");
            }
        }

        class ClosedToPendingEvt extends FSMEvent {
            protected ClosedToPendingEvt() {
                super("ClosedToPendingEvt");
            }
        }

        class OpenToSolvedEvt extends FSMEvent {
            protected OpenToSolvedEvt() {
                super("OpenToSolvedEvt");
            }
        }
        class OpenToClosedEvt extends FSMEvent {
            protected OpenToClosedEvt() {
                super("OpenToClosedEvt");
            }
        }
        class OpenToPendingEvt extends FSMEvent {
            protected OpenToPendingEvt() {
                super("OpenToPendingEvt");
            }
        }

        class PendingToOpenEvt extends FSMEvent {
            protected PendingToOpenEvt() {
                super("PendingToOpenEvt");
            }
        }
        class PendingToSolvedEvt extends FSMEvent {
            protected PendingToSolvedEvt() {
                super("PendingToSolvedEvt");
            }
        }
        class PendingToClosedEvt extends FSMEvent {
            protected PendingToClosedEvt() {
                super("PendingToClosedEvt");
            }
        }

        class SolvedToOpenEvt extends FSMEvent {
            protected SolvedToOpenEvt() {
                super("SolvedToOpenEvt");
            }
        }
        class SolvedToPendingEvt extends FSMEvent {
            protected SolvedToPendingEvt() {
                super("SolvedToPendingEvt");
            }
        }
        class SolvedToClosedEvt extends FSMEvent {
            protected SolvedToClosedEvt() {
                super("SolvedToClosedEvt");
            }
        }



        FSMState created = new FSMState("待分配");
        FSMState open = new FSMState("待受理");
        FSMState pending = new FSMState("受理中");
        FSMState solved = new FSMState("已解决");
        FSMState closed = new FSMState("已关闭");

        Set<FSMState> states = new HashSet<>();
        states.add(created);
        states.add(open);
        states.add(pending);
        states.add(solved);
        states.add(closed);


        FSMEventHandler defEvtHandler = evt -> {
            // 1. 是否可转移检测，不满足条件抛异常

            // 2. 逻辑
            System.out.println("transition :" + evt);
        };

        FSMTransition createdToOpen = FSMTransition.builder()
                .name("createdToOpen")
                .from(created)
                .to(open)
                .on(CreatedToOpenEvt.class, defEvtHandler)
                .build();



        FSMTransition closedToPending = FSMTransition.builder()
                .name("pendingToSolved")
                .from(closed)
                .to(pending)
                .on(ClosedToPendingEvt.class, defEvtHandler)
                .build();

        FSMTransition openToSolved = FSMTransition.builder()
                .name("openToSolved")
                .from(open)
                .to(solved)
                .on(OpenToSolvedEvt.class, defEvtHandler)
                .build();
        FSMTransition openToClosed = FSMTransition.builder()
                .name("openToClosed")
                .from(open)
                .to(closed)
                .on(OpenToClosedEvt.class, defEvtHandler)
                .build();
        FSMTransition openToPending = FSMTransition.builder()
                .name("openToPending")
                .from(open)
                .to(pending)
                .on(OpenToPendingEvt.class, defEvtHandler)
                .build();



        FSMTransition pendingToOpen = FSMTransition.builder()
                .name("pendingToOpen")
                .from(pending)
                .to(open)
                .on(PendingToOpenEvt.class, defEvtHandler)
                .build();
        FSMTransition pendingToSolved = FSMTransition.builder()
                .name("pendingToSolved")
                .from(pending)
                .to(solved)
                .on(PendingToSolvedEvt.class, defEvtHandler)
                .build();
        FSMTransition pendingToClosed = FSMTransition.builder()
                .name("pendingToClosed")
                .from(pending)
                .to(closed)
                .on(PendingToClosedEvt.class, defEvtHandler)
                .build();



        FSMTransition solvedToOpen = FSMTransition.builder()
                .name("solvedToOpen")
                .from(solved)
                .to(open)
                .on(SolvedToOpenEvt.class, defEvtHandler)
                .build();
        FSMTransition solvedToPending = FSMTransition.builder()
                .name("solvedToPending")
                .from(solved)
                .to(pending)
                .on(SolvedToPendingEvt.class, defEvtHandler)
                .build();
        FSMTransition solvedToClosed = FSMTransition.builder()
                .name("solvedToClosed")
                .from(solved)
                .to(closed)
                .on(SolvedToClosedEvt.class, defEvtHandler)
                .build();








        FSM ticketFsm = FSM.builder(states, created)
                .transition(createdToOpen)
                .transition(closedToPending)
                .transition(openToSolved,openToClosed,openToPending)
                .transition(pendingToOpen,pendingToSolved,pendingToClosed)
                .transition(solvedToOpen,solvedToPending,solvedToClosed)
                // .finalState(closed)
                .build();

        ticketFsm.fire(new CreatedToOpenEvt());
        ticketFsm.fire(new OpenToPendingEvt());
        ticketFsm.fire(new PendingToSolvedEvt());
        ticketFsm.fire(new SolvedToClosedEvt());

        ticketFsm.fire(new ClosedToPendingEvt());

        System.out.println(ticketFsm.getCurrentState());

//        System.out.println(ticketFsm.getReachableTransitions(ticketFsm.getCurrentState()));

    }

    // @Test
    public void testFSM() {
        class CoinEvent extends FSMEvent {
            private CoinEvent() {
                super("CoinEvent");
            }
        }
        class PushEvent extends FSMEvent {
            private PushEvent() {
                super("PushEvent");
            }
        }

        FSMState locked = new FSMState("locked");
        FSMState unlocked = new FSMState("unlocked");

        Set<FSMState> states = new HashSet<>();
        states.add(locked);
        states.add(unlocked);

        FSMTransition unlock = FSMTransition.builder()
                .name("unlock")
                .on(CoinEvent.class, evt -> {
                    System.out.println("Notified about event '" + evt.getName());
                    System.out.println("Unlocking turnstile..");
                })
                .from(locked)
                .to(unlocked)
                .build();

        FSMTransition pushLocked = FSMTransition.builder()
                .name("pushLocked")
                .on(PushEvent.class)
                .from(locked)
                .to(locked)
                .build();

        FSMTransition lock = FSMTransition.builder()
                .name("lock")
                .on(PushEvent.class, evt -> {
                    System.out.println("Notified about event '" + evt.getName());
                    System.out.println("Locking turnstile..");
                })
                .from(unlocked)
                .to(locked)
                .build();

        FSMTransition coinUnlocked = FSMTransition.builder()
                .name("coinUnlocked")
                .on(CoinEvent.class)
                .from(unlocked)
                .to(unlocked)
                .build();

        FSM fsm = FSM.builder(states, locked)
                .transition(lock)
                .transition(pushLocked)
                .transition(unlock)
                .transition(coinUnlocked)
                .build();


        System.out.println("Turnstile initial state : " + fsm.getCurrentState().getName());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Which event do you want to fire?");
        System.out.println("1. Push [p]");
        System.out.println("2. Coin [c]");
        System.out.println("Press [q] to quit tutorial.");


        while (true) {
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("p")) {
                System.out.println("input = " + input.trim());
                System.out.println("Firing push event..");
                fsm.fire(new PushEvent());
                System.out.println("Turnstile state : " + fsm.getCurrentState().getName());
            }
            if (input.trim().equalsIgnoreCase("c")) {
                System.out.println("input = " + input.trim());
                System.out.println("Firing coin event..");
                fsm.fire(new CoinEvent());
                System.out.println("Turnstile state : " + fsm.getCurrentState().getName());
            }
            if (input.trim().equalsIgnoreCase("q")) {
                System.out.println("input = " + input.trim());
                System.out.println("Bye!");
                System.exit(0);
            }

        }

    }
}
