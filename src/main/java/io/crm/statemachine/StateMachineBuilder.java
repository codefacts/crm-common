package io.crm.statemachine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.crm.statemachine.ex.StateMachineException;

import java.util.*;

/**
 * Created by shahadat on 4/27/16.
 */
public class StateMachineBuilder {

    private final String initialState;

    private final Map<String, Set<String>> stateEvents = new HashMap<>();
    private final Map<String, Set<String>> eventStates = new HashMap<>();
    private final Map<String, StateCallbacks> stateMap = new HashMap<>();

    public StateMachineBuilder(String initialState) {
        this.initialState = initialState;
    }

    public StateMachineBuilder from(String state, StateEventsRegistry... stateEvents) {
        Objects.requireNonNull(state);

        final HashSet<String> eSet = new HashSet<>();

        Arrays.asList(stateEvents).forEach(registry -> {

            if (eSet.contains(registry.event)) {
                throw new StateMachineException("Duplicate event: '" + registry.event + "' in the eventList for State: '" + state + "'");
            }
            eSet.add(registry.event);

            Set<String> stateSet = eventStates.get(registry.event);
            if (stateSet == null) {
                stateSet = new HashSet<>();
                stateSet.add(registry.state);
                eventStates.put(registry.event, stateSet);
            } else {
                stateSet.add(registry.state);
            }
        });

        if (this.stateEvents.containsKey(state)) {
            throw new StateMachineException("Duplicate state: '" + state + "' in the stateList.");
        }
        this.stateEvents.put(state, ImmutableSet.copyOf(eSet));

        return this;
    }

    public <T,R> StateMachineBuilder from(String state, StateEventsRegistry[] stateEvents, StateCallbacks<T,R> stateCallbacks) {

        from(state, stateEvents);
        callbacks(state, stateCallbacks);
        return this;
    }

    public <T, R> StateMachineBuilder callbacks(String state, StateCallbacks<T, R> registry) {

        if (stateMap.containsKey(state)) {
            throw new StateMachineException("Duplicate state: '" + state + "' in the stateCallbacksList.");
        }

        stateMap.put(state, registry);
        return this;
    }

    public StateMachine build() {

        Objects.requireNonNull(initialState);

        if (stateEvents.size() != stateMap.size()) {
            throw new StateMachineException("callbacks registry count: " + stateMap.size() + " and states name count " + stateEvents.size() + " must be equal.");
        }

        return new StateMachine(initialState, ImmutableMap.copyOf(stateEvents),
            immutableCopy(eventStates), ImmutableMap.copyOf(stateMap));
    }

    private Map<String, Set<String>> immutableCopy(Map<String, Set<String>> eventStates) {

        final ImmutableMap.Builder<String, Set<String>> builder = ImmutableMap.builder();

        eventStates.forEach((s, strings) -> builder.put(s, ImmutableSet.copyOf(strings)));

        return builder.build();
    }

    public static void main(String[] args) {
        final StateEventsRegistry so = States.on("").to("so");
    }
}
