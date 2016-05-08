package statemachine;

import io.netty.util.concurrent.Promise;

import java.util.concurrent.Callable;

/**
 * Created by Khan on 5/7/2016.
 */
public class StateCallbacks<T, R> {
    private final io.crm.
    private final Callable<Promise<?>> onExit;

    StateCallbacks(Callable<Promise<StateTrigger>> onEnter, Callable<Promise<?>> onExit) {
        this.onEnter = onEnter;
        this.onExit = onExit;
    }
}
