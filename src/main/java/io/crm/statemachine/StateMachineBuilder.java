package io.crm.statemachine;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.crm.statemachine.ex.StateMachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by shahadat on 4/27/16.
 */
public class StateMachineBuilder {

    public static final Logger LOGGER = LoggerFactory.getLogger(StateMachineBuilder.class);

    private String initialState;

    private final Map<String, Set<String>> stateEvents = new HashMap<>();
    private final Map<String, Set<String>> eventStates = new HashMap<>();
    private final Map<String, StateCallbacks> stateMap = new HashMap<>();

    public StateMachineBuilder() {
    }

    public StateMachineBuilder(String initialState) {
        this.initialState = initialState;
    }

    public String getInitialState() {
        return initialState;
    }

    public StateMachineBuilder setInitialState(String initialState) {
        this.initialState = initialState;
        return this;
    }

    public StateMachineBuilder from(String state, StateEventsRegistry... stateEvents) {
        Objects.requireNonNull(state);

        Set<String> ss = this.stateEvents.get(state);

        if (ss == null) {
            ss = new HashSet<>();
            this.stateEvents.put(state, ss);
        }

        final Set<String> eSet = ss;

        Arrays.asList(stateEvents).forEach(registry -> {

            eSet.add(registry.event);

            Set<String> stateSet = eventStates.get(registry.event);
            if (stateSet == null) {
                stateSet = new HashSet<>();
                eventStates.put(registry.event, stateSet);
            }

            stateSet.add(registry.state);

        });

        return this;
    }

    public <T, R> StateMachineBuilder from(String state, StateEventsRegistry[] stateEvents, StateCallbacks<T, R> stateCallbacks) {

        from(state, stateEvents);
        callbacks(state, stateCallbacks);
        return this;
    }

    public <T, R> StateMachineBuilder callbacks(String state, StateCallbacks<T, R> registry) {

        if (stateMap.containsKey(state)) {
            LOGGER.info("STATE CALLBACK IS OVER WRITTEN FOR STATE '" + state + "'");
        }

        stateMap.put(state, registry);
        return this;
    }

    public StateMachine build() {

        Objects.requireNonNull(initialState);

        return new StateMachine(initialState, immutableCopy(stateEvents),
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
