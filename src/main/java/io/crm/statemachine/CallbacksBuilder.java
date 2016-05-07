package io.crm.statemachine;

import io.crm.promise.intfs.Promise;
import io.crm.util.Context;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class CallbacksBuilder<T, R> {
    Consumer<T> onEnter;
    Consumer<T> onExit;
    Function<T, Promise<Map<String, Object>>> initialize;
    BiFunction<Context, T, Promise<StateContext<R>>> execute;
    BiFunction<Context, T, Promise<Void>> cleanup;

    public CallbacksBuilder<T, R> setOnEnter(Consumer<T> onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    public CallbacksBuilder<T, R> setOnExit(Consumer<T> onExit) {
        this.onExit = onExit;
        return this;
    }

    public CallbacksBuilder<T, R> setInitialize(Function<T, Promise<Map<String, Object>>> initialize) {
        this.initialize = initialize;
        return this;
    }

    public CallbacksBuilder<T, R> setExecute(BiFunction<Context, T, Promise<StateContext<R>>> execute) {
        this.execute = execute;
        return this;
    }

    public CallbacksBuilder<T, R> setCleanup(BiFunction<Context, T, Promise<Void>> cleanup) {
        this.cleanup = cleanup;
        return this;
    }

    public StateCallbacks createState() {
        return new StateCallbacks(onEnter, onExit, initialize, execute, cleanup);
    }

    public static <TT, RR> CallbacksBuilder<TT, RR> getInstance() {
        return new CallbacksBuilder<>();
    }
}