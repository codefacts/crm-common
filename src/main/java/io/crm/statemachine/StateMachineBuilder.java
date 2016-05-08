package statemachine;

import java.util.*;

public class StateMachineBuilder {
    private String initialState;
    private final Map<String, Set<String>> stateEvents = new HashMap<>();
    private final Map<String, Set<String>> eventStates = new HashMap<>();
    private final Map<String, StateCallbacks> stateCallbacksMap = new HashMap<>();

    private StateMachineBuilder() {
    }

    public static StateMachineBuilder newInstance() {
        return new StateMachineBuilder();
    }

    StateMachineBuilder setInitialState(String initialState) {
        this.initialState = initialState;
        return this;
    }

    public StateMachineBuilder when(String state, StateEntry... stateEntries) {
        Set<String> events = stateEvents.get(state);
        if (events == null) {
            events = new HashSet<>();
            stateEvents.put(state, events);
        }

        final Set<String> eventSet = events;
        Arrays.asList(stateEntries).forEach(entry -> {
            eventSet.add(entry.event);

            Set<String> states = eventStates.get(entry.event);
            if (states == null) {
                states = new HashSet<>();
                eventStates.put(entry.event, states);
            }

            states.add(entry.state);
        });

        return this;
    }

    public StateMachineBuilder callback(String state, StateCallbacks stateCallbacks) {
        this.stateCallbacksMap.put(state, stateCallbacks);
        return this;
    }

    public StateMachine build() {
        if (stateCallbacksMap.size() < stateEvents.size()) {
            throw new StateMachineException("State callbacks are missing for some states.");
        }

        if (initialState == null) throw new StateMachineException("Initial state is required.");

        return new StateMachine(initialState, stateEvents, eventStates, stateCallbacksMap);
    }

    public static void main(String[] args) {
        newInstance()
                .setInitialState("start")
                .when("start",
                        StateEntry.on("userIdNotFount", "validationError"),
                        StateEntry.on("bad", "back"))
                .when("transform",
                        StateEntry.on("ok", "end"))
                .callback("start", f2.createStateCallbacksBuilder().setOnEnter(null).setOnExit(null).build())
                .build();
    }
}