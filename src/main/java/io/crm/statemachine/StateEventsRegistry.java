package io.crm.statemachine;

/**
 * Created by shahadat on 4/28/16.
 */
public class StateEventsRegistry {
    final String event;
    final String state;

    public StateEventsRegistry(String event, String state) {
        this.event = event;
        this.state = state;
    }
}
