package io.crm.statemachine;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Promise;
import io.crm.statemachine.ex.StateMachineException;
import io.crm.util.Context;
import io.crm.util.touple.MutableTpl1;

import java.util.Map;
import java.util.Set;

/**
 * Created by shahadat on 4/27/16.
 */
final public class StateMachine {
    private final String initialState;
    private final Map<String, Set<String>> stateEvents;
    private final Map<String, Set<String>> eventStates;
    private final Map<String, StateCallbacks> stateMap;

    public StateMachine(String initialState, Map<String, Set<String>> stateEvents, Map<String, Set<String>> eventStates, Map<String, StateCallbacks> stateMap) {
        this.initialState = initialState;
        this.stateEvents = stateEvents;
        this.eventStates = eventStates;
        this.stateMap = stateMap;
    }

    public <T> Promise<Void> start(T msg) {

        try {

            final StateCallbacks<Object, Object> registry = stateMap.get(initialState);

            final Promise<StateContext<Object>> map = execute(registry, msg);

            return map.mapToPromise(this::executeNext).map(v -> null);

        } catch (Exception ex) {
            return Promises.fromError(ex);
        }

    }

    private Promise<StateContext<Object>> executeNext(StateContext<Object> stateContext) {

        if (stateContext == null) {
            return Promises.from(null);
        }

        final String event = stateContext.getEvent();
        if (event != null) {

            final StateCallbacks stateCallbacks = stateMap.get(event);

            if (stateCallbacks != null) {

                return execute(stateCallbacks, stateContext.getResult())
                    .mapToPromise(stateCtx -> executeNext((StateContext<Object>) stateCtx));

            } else {
                return Promises.<StateContext<Object>>fromError(new StateMachineException("State Mapping for event '" + event + "' not found."));
            }

        } else {
            return Promises.<StateContext<Object>>from(null);
        }
    }

    private <T> Promise<StateContext<Object>> execute(StateCallbacks<Object, Object> registry, T msg) {

        try {

            registry.onEnter.accept(msg);

            final MutableTpl1<StateContext> tpl1 = new MutableTpl1<>();

            return registry
                .initialize.apply(msg)
                .mapToPromise(map -> registry.execute.apply(new Context(map), msg))
                .then(val -> tpl1.t1 = val)
                .map(StateContext::getMap)
                .mapToPromise(map1 -> registry.cleanup.apply(new Context(map1), msg))
                .then(aVoid -> registry.onExit.accept(msg))
                .map(v -> tpl1.t1)
                ;

        } catch (Exception ex) {
            return Promises.fromError(ex);
        }
    }
}
