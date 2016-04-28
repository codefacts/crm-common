package io.crm.statemachine;

import io.crm.promise.intfs.Promise;
import io.crm.util.Context;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class CallbacksBuilder<T> {
    private Consumer<T> onEnter;
    private Consumer<T> onExit;
    private Function<T, Promise<Map<String, Object>>> initialize;
    private BiFunction<Context, T, Promise<Map<String, Object>>> execute;
    private BiFunction<Context, T, Promise<Void>> cleanup;

    public CallbacksBuilder<T> setOnEnter(Consumer<T> onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    public CallbacksBuilder<T> setOnExit(Consumer<T> onExit) {
        this.onExit = onExit;
        return this;
    }

    public CallbacksBuilder<T> setInitialize(Function<T, Promise<Map<String, Object>>> initialize) {
        this.initialize = initialize;
        return this;
    }

    public CallbacksBuilder<T> setExecute(BiFunction<Context, T, Promise<Map<String, Object>>> execute) {
        this.execute = execute;
        return this;
    }

    public CallbacksBuilder<T> setCleanup(BiFunction<Context, T, Promise<Void>> cleanup) {
        this.cleanup = cleanup;
        return this;
    }

    public StateCallbacks createState() {
        return new StateCallbacks(onEnter, onExit, initialize, execute, cleanup);
    }

    public static <T> CallbacksBuilder<T> getInstance() {
        return new CallbacksBuilder<>();
    }
}