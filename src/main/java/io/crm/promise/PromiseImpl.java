package io.crm.promise;

import io.crm.promise.intfs.*;

/**
 * Created by someone on 15/10/2015.
 */
final public class PromiseImpl<T> implements Promise<T>, Defer<T> {
    public static long total = 0;
    private static final MapHandler emptyMapHandler = s -> s;
    private static final MapPromiseHandler emptyMapPromiseHandler = s -> Promises.success(s);
    private static final ThenHandler emptyThenHandler = s -> {
    };
    private static final SuccessHandler emptySuccessHandler = s -> {
    };
    private static final ErrorHandler emptyErrorHandler = s -> {
    };
    private static final CompleteHandler emptyCompleteHandler = s -> {
    };

    PromiseImpl() {
        total++;
    }

    private T value;
    private Throwable error;
    private State state;

    private Invokable invokeNext;
    private Defer deferNext;

    @Override
    public void fail(Throwable throwable) {
        if (((state == State.error) | (state == State.success))) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        error = throwable;
        state = State.error;
        if (deferNext == null) return;
        try {
            if (invokeNext instanceof ErrorHandler) {
                ((ErrorHandler) invokeNext).accept(error);
            } else if (invokeNext instanceof CompleteHandler) {
                ((CompleteHandler) invokeNext).accept(this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            error.addSuppressed(ex);
        }
        deferNext.fail(error);
    }

    @Override
    public void complete() {
        if (((state == State.error) | (state == State.success))) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        state = State.success;
        if (deferNext == null) return;
        try {
            if (invokeNext instanceof MapHandler) {
                final Object retValue = ((MapHandler) invokeNext).apply(value);
                deferNext.complete(retValue);
            } else if (invokeNext instanceof MapPromiseHandler) {
                final Promise promise = ((MapPromiseHandler) invokeNext).apply(value);
                promise.error(e -> deferNext.fail(e));
                promise.success(s -> deferNext.complete(s));
            } else if (invokeNext instanceof ThenHandler) {
                ((ThenHandler) invokeNext).accept(value);
                deferNext.complete();
            } else if (invokeNext instanceof SuccessHandler) {
                ((SuccessHandler) invokeNext).accept(value);
                deferNext.complete(value);
            } else if (invokeNext instanceof CompleteHandler) {
                ((CompleteHandler) invokeNext).accept(this);
                deferNext.complete(value);
            } else {
                deferNext.complete(value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            deferNext.fail(ex);
        }
    }

    @Override
    public void complete(T value) {
        if (((state == State.error) | (state == State.success))) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        this.value = value;
        state = State.success;
        if (deferNext == null) return;
        try {
            if (invokeNext instanceof MapHandler) {
                final Object retValue = ((MapHandler) invokeNext).apply(value);
                deferNext.complete(retValue);
            } else if (invokeNext instanceof MapPromiseHandler) {
                final Promise promise = ((MapPromiseHandler) invokeNext).apply(value);
                promise.error(e -> deferNext.fail(e));
                promise.success(s -> deferNext.complete(s));
            } else if (invokeNext instanceof ThenHandler) {
                ((ThenHandler) invokeNext).accept(value);
                deferNext.complete();
            } else if (invokeNext instanceof SuccessHandler) {
                ((SuccessHandler) invokeNext).accept(value);
                deferNext.complete(value);
            } else if (invokeNext instanceof CompleteHandler) {
                ((CompleteHandler) invokeNext).accept(this);
                deferNext.complete(value);
            } else {
                deferNext.complete(value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            deferNext.fail(ex);
        }
    }

    @Override
    public Promise<T> promise() {
        return this;
    }

    private void invokeErrorCallback() {
        if (deferNext == null) return;
        try {
            if (invokeNext instanceof ErrorHandler) {
                ((ErrorHandler) invokeNext).accept(error);
            } else if (invokeNext instanceof CompleteHandler) {
                ((CompleteHandler) invokeNext).accept(this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            error.addSuppressed(ex);
        }
        deferNext.fail(error);
    }

    private void invokeSuccessCallback() {
        if (deferNext == null) return;
        try {
            if (invokeNext instanceof MapHandler) {
                final Object retValue = ((MapHandler) invokeNext).apply(value);
                deferNext.complete(retValue);
            } else if (invokeNext instanceof MapPromiseHandler) {
                final Promise promise = ((MapPromiseHandler) invokeNext).apply(value);
                promise.error(e -> deferNext.fail(e));
                promise.success(s -> deferNext.complete(s));
            } else if (invokeNext instanceof ThenHandler) {
                ((ThenHandler) invokeNext).accept(value);
                deferNext.complete();
            } else if (invokeNext instanceof SuccessHandler) {
                ((SuccessHandler) invokeNext).accept(value);
                deferNext.complete(value);
            } else if (invokeNext instanceof CompleteHandler) {
                ((CompleteHandler) invokeNext).accept(this);
                deferNext.complete(value);
            } else {
                deferNext.complete(value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            deferNext.fail(ex);
        }
    }

    @Override
    public <R> Promise<R> mapTo(MapHandler<T, R> mapHandler) {
        final MapHandler<T, R> _mapHandler = mapHandler == null ? emptyMapHandler : mapHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>();
        deferNext = promise;
        if ((state == State.success)) {
            try {
                final R retVal = _mapHandler.apply(value);
                deferNext.complete(retVal);
            } catch (Exception ex) {
                ex.printStackTrace();
                deferNext.fail(ex);
            }
            return promise;
        } else if ((state == State.error)) {
            deferNext.fail(error);
            return promise;
        }
        invokeNext = _mapHandler;
        return promise;
    }

    @Override
    public <R> Promise<R> mapToPromise(MapPromiseHandler<T, R> promiseHandler) {
        final MapPromiseHandler<T, R> _promiseHandler = promiseHandler == null ? emptyMapPromiseHandler : promiseHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>();
        deferNext = promise;
        if ((state == State.success)) {
            try {
                final Promise<R> rPromise = _promiseHandler.apply(value);
                rPromise.success(s -> deferNext.complete(s)).error(e -> deferNext.fail(e));
            } catch (Exception ex) {
                ex.printStackTrace();
                deferNext.fail(ex);
            }
            return promise;
        } else if ((state == State.error)) {
            deferNext.fail(error);
            return promise;
        }
        invokeNext = _promiseHandler;
        return promise;
    }

    @Override
    public Promise<Void> mapToVoid(ThenHandler<T> thenHandler) {
        final ThenHandler<T> _thenHandler = thenHandler == null ? emptyThenHandler : thenHandler;
        final PromiseImpl<Void> promise = new PromiseImpl<>();
        deferNext = promise;
        if ((state == State.success)) {
            try {
                _thenHandler.accept(value);
                deferNext.complete();
            } catch (Exception ex) {
                ex.printStackTrace();
                deferNext.fail(ex);
            }
            return promise;
        } else if ((state == State.error)) {
            deferNext.fail(error);
            return promise;
        }
        invokeNext = _thenHandler;
        return promise;
    }

    @Override
    public Promise<T> success(SuccessHandler<T> successHandler) {
        final SuccessHandler<T> _successHandler = successHandler == null ? emptySuccessHandler : successHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>();
        deferNext = promise;
        if ((state == State.success)) {
            try {
                _successHandler.accept(value);
                deferNext.complete(value);
            } catch (Exception ex) {
                ex.printStackTrace();
                deferNext.fail(ex);
            }
            return promise;
        } else if ((state == State.error)) {
            deferNext.fail(error);
            return promise;
        }
        invokeNext = _successHandler;
        return promise;
    }

    @Override
    public Promise<T> error(ErrorHandler errorHandler) {
        final ErrorHandler _errorHandler = errorHandler == null ? emptyErrorHandler : errorHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>();
        deferNext = promise;
        if ((state == State.error)) {
            try {
                _errorHandler.accept(error);
                deferNext.fail(error);
            } catch (Exception ex) {
                ex.printStackTrace();
                error.addSuppressed(ex);
                deferNext.fail(error);
            }
            return promise;
        } else if ((state == State.success)) {
            deferNext.complete(value);
            return promise;
        }
        invokeNext = _errorHandler;
        return promise;
    }

    @Override
    public Promise<T> complete(CompleteHandler<T> completeHandler) {
        final CompleteHandler<T> _completeHandler = completeHandler == null ? emptyCompleteHandler : completeHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>();
        deferNext = promise;
        if ((state == State.success)) {
            try {
                _completeHandler.accept(this);
                deferNext.complete(value);
            } catch (Exception ex) {
                ex.printStackTrace();
                deferNext.fail(ex);
            }
            return promise;
        } else if ((state == State.error)) {
            try {
                _completeHandler.accept(this);
                deferNext.fail(error);
            } catch (Exception ex) {
                ex.printStackTrace();
                deferNext.fail(ex);
            }
            return promise;
        }
        invokeNext = _completeHandler;
        return promise;
    }

    @Override
    public boolean isComplete() {
        return ((state == State.error) | (state == State.success));
    }

    @Override
    public boolean isSuccess() {
        return (state == State.success);
    }

    @Override
    public boolean isError() {
        return (state == State.error);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getOrElse(T t) {
        return value == null ? t : value;
    }

    @Override
    public Throwable error() {
        return error;
    }

    public enum State {success, error}

    @Override
    public String toString() {
        return String.format("Promise[value: %s | error: %s | success: %s | error: %s | complete: %s]",
                value, error, (state == State.success), (state == State.error), ((state == State.error) | (state == State.success)));
    }

    public static void main(String... args) throws Exception {

    }
}
