package io.crm.statemachine;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by shahadat on 4/27/16.
 */
public class StateMachineBuilder {
    private final String initialState;
    private final ImmutableMap.Builder<String, Set<String>> stateEvents = ImmutableMap.builder();
    private final ImmutableMap.Builder<String, Set<String>> eventStates = ImmutableMap.builder();
    private final ImmutableMap.Builder<String, State> stateMap = ImmutableMap.builder();

    public StateMachineBuilder(String initialState) {
        this.initialState = initialState;
    }

    public

    public StateMachine build() {
        Objects.requireNonNull(initialState);
        return new StateMachine(initialState, stateEvents.build(), eventStates.build(), stateMap.build());
    }
}
