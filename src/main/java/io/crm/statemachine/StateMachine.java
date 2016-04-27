package io.crm.statemachine;

import java.util.Map;
import java.util.Set;

/**
 * Created by shahadat on 4/27/16.
 */
final public class StateMachine {
    private final String initialState;
    private final Map<String, Set<String>> stateEvents;
    private final Map<String, Set<String>> eventStates;
    private final Map<String, State> stateMap;

    public StateMachine(String initialState, Map<String, Set<String>> stateEvents, Map<String, Set<String>> eventStates, Map<String, State> stateMap) {
        this.initialState = initialState;
        this.stateEvents = stateEvents;
        this.eventStates = eventStates;
        this.stateMap = stateMap;
    }
}
