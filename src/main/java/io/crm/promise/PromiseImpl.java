package io.crm.promise;

import io.crm.promise.intfs.*;

/**
 * Created by someone on 15/10/2015.
 */
final public class PromiseImpl<T> implements Promise<T>, Defer<T> {
    public static long total = 0;
    private static final MapHandler emptyMapHandler = s -> s;
    private static final MapPromiseHandler emptyMapPromiseHandler = s -> Promises.from(s);
    private static final ThenHandler emptyThenHandler = s -> {
    };
    private static final SuccessHandler emptySuccessHandler = s -> {
    };
    private static final ErrorHandler emptyErrorHandler = s -> {
    };
    private static final CompleteHandler emptyCompleteHandler = s -> {
    };

    private T value;
    private Throwable error;
    private State state;

    private final Invokable callback;
    private final Type type;
    private PromiseImpl nextPromise;

    PromiseImpl(final Invokable callback, final Type type) {
        this.callback = callback;
        this.type = type;
        total++;
    }

    @Override
    public void fail(final Throwable throwable) {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        error = throwable;
        state = State.error;
        invokeErrorCallback(nextPromise, error, this);
    }

    @Override
    public void complete() {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        state = State.success;
        invokeSuccessCallback(nextPromise, value, this);
    }

    @Override
    public void complete(final T value) {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        this.value = value;
        state = State.success;
        invokeSuccessCallback(nextPromise, value, this);
    }

    @Override
    public Promise<T> promise() {
        return this;
    }

    private void invokeErrorCallback(PromiseImpl<T> nextPromise, Throwable error, PromiseImpl $this) {
        for (; ; ) {
            if (nextPromise == null) {
                return;
            }
            try {
                final Invokable _invokeNext = nextPromise.callback;
                if (_invokeNext instanceof ErrorHandler) {
                    ((ErrorHandler) _invokeNext).accept(error);
                } else if (_invokeNext instanceof CompleteHandler) {
                    ((CompleteHandler) _invokeNext).accept($this);
                }
            } catch (final Exception ex) {
                ex.printStackTrace();
                error.addSuppressed(ex);
            }

            nextPromise.error = error;
            nextPromise.state = State.error;
            $this = nextPromise;
            error = nextPromise.error;
            nextPromise = nextPromise.nextPromise;
        }
    }

    private void invokeSuccessCallback(PromiseImpl nextPromise, Object value, PromiseImpl $this) {
        for (; ; ) {
            if (nextPromise == null) return;
            try {

                final Invokable _invokeNext = nextPromise.callback;

                if (nextPromise.type == Type.MapTo) {

                    final Object retValue = ((MapHandler) _invokeNext).apply(value);
                    value = nextPromise.value = retValue;

                } else if (_invokeNext instanceof MapPromiseHandler) {

                    final Promise promise = ((MapPromiseHandler) _invokeNext).apply(value);
                    final PromiseImpl pp = nextPromise;
                    promise
                            .error(e ->
                                    pp.fail(e))
                            .then(s ->
                                    pp.complete(s));

                    return;

                } else if (_invokeNext instanceof ThenHandler) {

                    ((ThenHandler) _invokeNext).accept(value);
                    value = null;

                } else if (_invokeNext instanceof SuccessHandler) {

                    ((SuccessHandler) _invokeNext).accept(value);
                    value = nextPromise.value = value;

                } else if (_invokeNext instanceof CompleteHandler) {

                    ((CompleteHandler) _invokeNext).accept($this);
                    value = nextPromise.value = value;

                } else {
                    value = nextPromise.value = value;
                }

                nextPromise.state = State.success;

                $this = nextPromise;
                nextPromise = nextPromise.nextPromise;

            } catch (final Exception ex) {
                ex.printStackTrace();
                nextPromise.fail(ex);
                return;
            }
        }
    }

    @Override
    public <R> Promise<R> mapTo(final MapHandler<T, R> mapHandler) {
        final MapHandler<T, R> _mapHandler = mapHandler == null ? emptyMapHandler : mapHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>(_mapHandler, Type.MapTo);
        final Defer _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                final R retVal = _mapHandler.apply(value);
                _deferNext.complete(retVal);
            } catch (final Exception ex) {
                ex.printStackTrace();
                _deferNext.fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext.fail(error);
            return promise;
        }
        return promise;
    }

    @Override
    public <R> Promise<R> mapToPromise(final MapPromiseHandler<T, R> promiseHandler) {
        final MapPromiseHandler<T, R> _promiseHandler = promiseHandler == null ? emptyMapPromiseHandler : promiseHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>(_promiseHandler, Type.MapToPromise);
        final Defer _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                final Promise<R> rPromise = _promiseHandler.apply(value);
                rPromise.then(s -> _deferNext.complete(s)).error(e -> _deferNext.fail(e));
            } catch (final Exception ex) {
                ex.printStackTrace();
                _deferNext.fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext.fail(error);
            return promise;
        }
        return promise;
    }

    @Override
    public Promise<Void> mapToVoid(final ThenHandler<T> thenHandler) {
        final ThenHandler<T> _thenHandler = thenHandler == null ? emptyThenHandler : thenHandler;
        final PromiseImpl<Void> promise = new PromiseImpl<>(_thenHandler, Type.MapToVoid);
        final Defer _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                _thenHandler.accept(value);
                _deferNext.complete();
            } catch (final Exception ex) {
                ex.printStackTrace();
                _deferNext.fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext.fail(error);
            return promise;
        }
        return promise;
    }

    @Override
    public Promise<T> then(final SuccessHandler<T> successHandler) {
        final SuccessHandler<T> _successHandler = successHandler == null ? emptySuccessHandler : successHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>(_successHandler, Type.SuccessHandler);
        final Defer _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                _successHandler.accept(value);
                _deferNext.complete(value);
            } catch (final Exception ex) {
                ex.printStackTrace();
                _deferNext.fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext.fail(error);
            return promise;
        }
        return promise;
    }

    @Override
    public Promise<T> error(final ErrorHandler errorHandler) {
        if (nextPromise != null)
            throw new PromiseCallbackAlreadySet("A call back for this promise is already set. " + toString());
        final ErrorHandler _errorHandler = errorHandler == null ? emptyErrorHandler : errorHandler;
        final PromiseImpl<T> promise = nextPromise = new PromiseImpl<>(_errorHandler, Type.ErrorHandler);
        if (isError()) {
            try {
                _errorHandler.accept(error);
                promise.fail(error);
            } catch (final Exception ex) {
                ex.printStackTrace();
                error.addSuppressed(ex);
                promise.fail(error);
            }
            return promise;
        } else if (isSuccess()) {
            promise.complete(value);
            return promise;
        }
        return promise;
    }

    @Override
    public Promise<T> complete(final CompleteHandler<T> completeHandler) {
        final CompleteHandler<T> _completeHandler = completeHandler == null ? emptyCompleteHandler : completeHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>(_completeHandler, Type.CompleteHandler);
        final Defer _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                _completeHandler.accept(this);
                _deferNext.complete(value);
            } catch (final Exception ex) {
                ex.printStackTrace();
                _deferNext.fail(ex);
            }
            return promise;
        } else if (isError()) {
            try {
                _completeHandler.accept(this);
                _deferNext.fail(error);
            } catch (final Exception ex) {
                ex.printStackTrace();
                _deferNext.fail(ex);
            }
            return promise;
        }
        return promise;
    }

    @Override
    public boolean isComplete() {
        return (state == State.error) | (state == State.success);
    }

    @Override
    public boolean isSuccess() {
        return state == State.success;
    }

    @Override
    public boolean isError() {
        return state == State.error;
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

    private enum State {success, error}

    private enum Type {
        MapTo,
        MapToPromise,
        MapToVoid,
        SuccessHandler,
        ErrorHandler,
        CompleteHandler
    }

    @Override
    public String toString() {
        return String.format("Promise[value: %s | error: %s | success: %s | error: %s | complete: %s]",
                value, error, isSuccess(), isError(), isComplete());
    }

    public static void main(String... args) throws Exception {

    }
}
