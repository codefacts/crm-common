package io.crm.statemachine;
import io.crm.intfs.FunctionUnchecked;
import io.crm.promise.intfs.Promise;

import java.util.concurrent.Callable;

/**
 * Created by Khan on 5/7/2016.
 */
public class StateCallbacks<T, R> {
    final FunctionUnchecked<T, Promise<StateTrigger<R>>> onEnter;
    final Callable<Promise<Void>> onExit;

    StateCallbacks(FunctionUnchecked<T, Promise<StateTrigger<R>>> onEnter, Callable<Promise<Void>> onExit) {
        this.onEnter = onEnter;
        this.onExit = onExit;
    }
}
