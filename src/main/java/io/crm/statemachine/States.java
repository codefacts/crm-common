package io.crm.statemachine;

/**
 * Created by shahadat on 4/27/16.
 */
final public class States {
    public static StateMachineBuilder from(String initialState) {
        return new StateMachineBuilder(initialState);
    }
}
