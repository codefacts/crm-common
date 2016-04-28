package io.crm.statemachine;

/**
 * Created by shahadat on 4/27/16.
 */
final public class States {
    public static StateMachineBuilder withInitial(String initialState) {
        return new StateMachineBuilder(initialState);
    }

    public static EventBuilder on(final String event) {
        return new EventBuilder(event);
    }
}
