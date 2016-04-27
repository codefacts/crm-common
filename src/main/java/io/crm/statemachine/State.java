package io.crm.statemachine;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Promise;
import io.crm.util.Context;

import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by shahadat on 4/27/16.
 */
final public class State<T> {
    public final Consumer<T> onEnter;
    public final Consumer<T> onExit;
    public final Function<T, Promise<Map<String, Object>>> initialize;
    public final BiFunction<Context, T, Promise<Map<String, Object>>> execute;
    public final BiFunction<Context, T, Promise<Void>> cleanup;

    public State(Consumer<T> onEnter, Consumer<T> onExit, Function<T, Promise<Map<String, Object>>> initialize, BiFunction<Context, T, Promise<Map<String, Object>>> execute, BiFunction<Context, T, Promise<Void>> cleanup) {
        this.onEnter = onEnter != null ? onEnter : t -> {
        };
        this.onExit = onExit != null ? onExit : t -> {
        };
        this.initialize = initialize != null ? initialize : t -> Promises.from(Collections.EMPTY_MAP);
        this.execute = execute != null ? execute : (context, t) -> Promises.from(Collections.EMPTY_MAP);
        this.cleanup = cleanup != null ? cleanup : (context, t) -> Promises.<Void>from();
    }
}
