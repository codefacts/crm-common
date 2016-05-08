package io.crm.statemachine;

import io.crm.intfs.FunctionUnchecked;
import io.crm.promise.intfs.Promise;

import java.util.concurrent.Callable;

public class StateCallbacksBuilder<T, R> {
    private FunctionUnchecked<T, Promise<StateTrigger<R>>> onEnter;
    private Callable<Promise<Void>> onExit;

    private StateCallbacksBuilder() {
    }

    public static <T, R> StateCallbacksBuilder<T, R> create() {
        return new StateCallbacksBuilder<>();
    }

    public StateCallbacksBuilder<T, R> onEnter(FunctionUnchecked<T, Promise<StateTrigger<R>>> onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    public StateCallbacksBuilder<T, R> onExit(Callable<Promise<Void>> onExit) {
        this.onExit = onExit;
        return this;
    }

    public StateCallbacks<T, R> build() {
        return new StateCallbacks<>(onEnter, onExit);
    }
}