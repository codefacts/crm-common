package io.crm.statemachine;

/**
 * Created by shahadat on 4/28/16.
 */
final public class EventBuilder {
    private final String event;

    public EventBuilder(String event) {
        this.event = event;
    }

    public StateEventsRegistry to(String state) {
        return new StateEventsRegistry(event, state);
    }
}
