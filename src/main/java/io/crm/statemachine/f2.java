package statemachine;

import io.netty.util.concurrent.Promise;

import java.util.concurrent.Callable;

public class f2 {
    private Callable<Promise<StateTrigger>> onEnter;
    private Callable<Promise<?>> onExit;

    private f2() {
    }

    static f2 createStateCallbacksBuilder() {
        return new f2();
    }

    public f2 setOnEnter(Callable<Promise<StateTrigger>> onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    public f2 setOnExit(Callable<Promise<?>> onExit) {
        this.onExit = onExit;
        return this;
    }

    public StateCallbacks build() {
        return new StateCallbacks(onEnter, onExit);
    }
}