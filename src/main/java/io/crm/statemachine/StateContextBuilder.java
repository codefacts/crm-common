package io.crm.statemachine;

import java.util.Collections;
import java.util.Map;

/**
 * Created by shahadat on 4/28/16.
 */
public class StateContextBuilder<R> {
    private Map<String, Object> map;
    private String event;
    private R result;

    public StateContextBuilder context(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    public StateContextBuilder trigger(String event) {
        this.event = event;
        return this;
    }

    public StateContextBuilder result(R result) {
        this.result = result;
        return this;
    }

    public StateContext<R> build() {
        return new StateContext<>(map, event, result);
    }
}
