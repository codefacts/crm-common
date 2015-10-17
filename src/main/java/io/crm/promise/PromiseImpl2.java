package io.crm.promise;

import io.crm.promise.intfs.*;

/**
 * Created by someone on 15/10/2015.
 */
final public class PromiseImpl2<T> implements Promise<T>, Defer<T> {
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

    private T value;
    private Throwable error;
    private State state;

    private final Invokable callback;
    private final Type type;
    private PromiseImpl2 deferNext;

    PromiseImpl2(final Invokable callback, final Type type) {
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
        invokeErrorCallback(deferNext, error, this);
    }

    @Override
    public void complete() {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        state = State.success;
        invokeSuccessCallback(deferNext, value, this);
    }

    @Override
    public void complete(final T value) {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        this.value = value;
        state = State.success;
        invokeSuccessCallback(deferNext, value, this);
    }

    @Override
    public Promise<T> promise() {
        return this;
    }

    private void invokeErrorCallback(PromiseImpl2<T> nextPromise, Throwable error, PromiseImpl2 $this) {
        for (; ; ) {
            if (nextPromise == null) return;
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
//            nextPromise.fail(error);
            nextPromise.error = error;
            nextPromise.state = State.error;
            $this = nextPromise;
            error = nextPromise.error;
            nextPromise = nextPromise.deferNext;
        }
    }

    private void invokeSuccessCallback(PromiseImpl2 nextPromise, Object value, PromiseImpl2 $this) {
        for (; ; ) {
            if (nextPromise == null) return;
            try {

                final Invokable _invokeNext = nextPromise.callback;

                if (nextPromise.type == Type.MapTo) {

                    final Object retValue = ((MapHandler) _invokeNext).apply(value);
                    nextPromise.value = retValue;

                } else if (_invokeNext instanceof MapPromiseHandler) {

                    final Promise promise = ((MapPromiseHandler) _invokeNext).apply(value);
                    final PromiseImpl2 pp = nextPromise;
                    promise.error(e -> pp.fail(e));
                    promise.success(s -> pp.complete(s));

                } else if (_invokeNext instanceof ThenHandler) {

                    ((ThenHandler) _invokeNext).accept(value);

                } else if (_invokeNext instanceof SuccessHandler) {

                    ((SuccessHandler) _invokeNext).accept(value);
                    nextPromise.value = value;

                } else if (_invokeNext instanceof CompleteHandler) {

                    ((CompleteHandler) _invokeNext).accept($this);
                    nextPromise.value = value;

                } else {
                    nextPromise.value = value;
                }

                nextPromise.state = State.success;

                $this = nextPromise;
                value = nextPromise.value;
                nextPromise = nextPromise.deferNext;

            } catch (final Exception ex) {
                ex.printStackTrace();
                nextPromise.fail(ex);
            }
        }
    }

    @Override
    public <R> Promise<R> mapTo(final MapHandler<T, R> mapHandler) {
        final MapHandler<T, R> _mapHandler = mapHandler == null ? emptyMapHandler : mapHandler;
        final PromiseImpl2<R> promise = new PromiseImpl2<>(_mapHandler, Type.MapTo);
        final Defer _deferNext = deferNext = promise;
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
        final PromiseImpl2<R> promise = new PromiseImpl2<>(_promiseHandler, Type.MapToPromise);
        final Defer _deferNext = deferNext = promise;
        if (isSuccess()) {
            try {
                final Promise<R> rPromise = _promiseHandler.apply(value);
                rPromise.success(s -> _deferNext.complete(s)).error(e -> _deferNext.fail(e));
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
        final PromiseImpl2<Void> promise = new PromiseImpl2<>(_thenHandler, Type.MapToVoid);
        final Defer _deferNext = deferNext = promise;
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
    public Promise<T> success(final SuccessHandler<T> successHandler) {
        final SuccessHandler<T> _successHandler = successHandler == null ? emptySuccessHandler : successHandler;
        final PromiseImpl2<T> promise = new PromiseImpl2<>(_successHandler, Type.SuccessHandler);
        final Defer _deferNext = deferNext = promise;
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
        final ErrorHandler _errorHandler = errorHandler == null ? emptyErrorHandler : errorHandler;
        final PromiseImpl2<T> promise = new PromiseImpl2<>(_errorHandler, Type.ErrorHandler);
        final Defer _deferNext = deferNext = promise;
        if (isError()) {
            try {
                _errorHandler.accept(error);
                _deferNext.fail(error);
            } catch (final Exception ex) {
                ex.printStackTrace();
                error.addSuppressed(ex);
                _deferNext.fail(error);
            }
            return promise;
        } else if (isSuccess()) {
            _deferNext.complete(value);
            return promise;
        }
        return promise;
    }

    @Override
    public Promise<T> complete(final CompleteHandler<T> completeHandler) {
        final CompleteHandler<T> _completeHandler = completeHandler == null ? emptyCompleteHandler : completeHandler;
        final PromiseImpl2<T> promise = new PromiseImpl2<>(_completeHandler, Type.CompleteHandler);
        final Defer _deferNext = deferNext = promise;
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
