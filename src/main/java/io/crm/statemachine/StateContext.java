package io.crm.statemachine;

import java.util.Map;

/**
 * Created by shahadat on 4/28/16.
 */
public class StateContext<R> {
    private final Map<String, Object> map;
    private final String event;
    private final R result;

    public StateContext(Map<String, Object> map, String event, R result) {
        this.map = map;
        this.event = event;
        this.result = result;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public String getEvent() {
        return event;
    }

    public R getResult() {
        return result;
    }
}
