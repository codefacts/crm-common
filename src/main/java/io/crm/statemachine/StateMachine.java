package statemachine;

import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.Set;

/**
 * Created by Khan on 5/7/2016.
 */
public class StateMachine {
    private final String initialState;
    private final Map<String, Set<String>> stateEvents;
    private final Map<String, Set<String>> eventStates;
    private final Map<String, StateCallbacks> stateCallbacksMap;

    public StateMachine(String initialState, Map<String, Set<String>> stateEvents, Map<String, Set<String>> eventStates, Map<String, StateCallbacks> stateCallbacksMap) {
        this.initialState = initialState;
        this.stateEvents = stateEvents;
        this.eventStates = eventStates;
        this.stateCallbacksMap = stateCallbacksMap;
    }

    public <T, R> Promise<R> start(T message) {

        return null;
    }

    private Promise<Object> execute(StateCallbacks stateCallbacks) {
        stateCallbacks.o
    }
}
