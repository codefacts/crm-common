package io.crm.promise;

import io.crm.promise.intfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by someone on 15/10/2015.
 */
final public class PromiseImpl<T> implements Promise<T>, Defer<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger(PromiseImpl.class);
    public static long total = 0;
    private static final MapToHandler EMPTY_MAP_TO_HANDLER = s -> s;
    private static final MapToPromiseHandler EMPTY_MAP_TO_PROMISE_HANDLER = s -> Promises.from(s);
    private static final ThenHandler emptyThenHandler = s -> {
    };
    private static final SuccessHandler emptySuccessHandler = s -> {
    };
    private static final ErrorHandler emptyErrorHandler = s -> {
    };
    private static final CompleteHandler emptyCompleteHandler = s -> {
    };
    private FilterHandler emptyFilterHandler = s -> true;

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
        _fail(throwable);
        logError(throwable);
    }

    private void _fail(final Throwable ex) {
        error = ex;
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
                logError(ex);
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

                    final Object retValue = ((MapToHandler) _invokeNext).apply(value);
                    value = nextPromise.value = retValue;

                } else if (_invokeNext instanceof MapToPromiseHandler) {

                    final Promise promise = ((MapToPromiseHandler) _invokeNext).apply(value);

                    if (promise == null) {
                        throw new NullPointerException("No Promise was returned from the mapToPromise " +
                            "Handler for value: " + value);
                    }

                    final PromiseImpl pp = nextPromise;
                    promise
                        .error(e ->
                            pp._fail(e))
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
                logError(ex);
                nextPromise._fail(ex);
                return;
            }
        }
    }

    @Override
    public <R> Promise<R> map(final MapToHandler<T, R> mapToHandler) {
        final MapToHandler<T, R> _mapToHandler = mapToHandler == null ? EMPTY_MAP_TO_HANDLER : mapToHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>(_mapToHandler, Type.MapTo);
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                final R retVal = _mapToHandler.apply(value);
                _deferNext.complete(retVal);
            } catch (final Exception ex) {
                logError(ex);
                _deferNext._fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext._fail(error);
            return promise;
        }
        return promise;
    }

    @Override
    public <R> Promise<R> mapToPromise(final MapToPromiseHandler<T, R> promiseHandler) {
        final MapToPromiseHandler<T, R> _promiseHandler = promiseHandler == null ? EMPTY_MAP_TO_PROMISE_HANDLER : promiseHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>(_promiseHandler, Type.MapToPromise);
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                final Promise<R> rPromise = _promiseHandler.apply(value);
                if (rPromise == null) {
                    throw new NullPointerException("No Promise was returned from the mapToPromise Handler for value: " + value);
                }
                rPromise.then(s -> _deferNext.complete(s)).error(e -> _deferNext._fail(e));
            } catch (final Exception ex) {
                logError(ex);
                _deferNext._fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext._fail(error);
            return promise;
        }
        return promise;
    }

    @Override
    public <R> ConditionalPromise<R> decideAndMap(MapAndDecideHandler<T, R> functionUnchecked) {
        final PromiseImpl<Decision<R>> decisionDefer = new PromiseImpl<>(null, null);
        final ConditionalPromiseImpl<R> router = new ConditionalPromiseImpl<>(decisionDefer.promise());
        this.map(functionUnchecked::apply)
            .then(decision -> {
                decisionDefer.complete(decision);
            })
            .error(decisionDefer::_fail)
        ;
        return router;
    }

    @Override
    public <R> ConditionalPromise<R> decideAndMapToPromise(MapToPromiseAndDecideHandler<T, R> function) {
        final PromiseImpl<Decision<R>> decisionDefer = new PromiseImpl<>(null, null);
        final ConditionalPromiseImpl<R> router = new ConditionalPromiseImpl<>(decisionDefer.promise());
        this.map(function::apply)
            .mapToPromise(decision -> decision.retVal.then(val -> {
                decisionDefer.complete(Decision.of(decision.decision, val));
            }))
            .error(decisionDefer::_fail)
        ;
        return router;
    }

    @Override
    public ConditionalPromise<Void> decide(ThenDecideHandler<T> valueConsumer) {
        final PromiseImpl<Decision<Void>> decisionDefer = new PromiseImpl<>(null, null);
        final ConditionalPromiseImpl<Void> router = new ConditionalPromiseImpl<>(decisionDefer.promise());
        this.map(valueConsumer::apply)
            .then(decision -> {
                decisionDefer.complete(Decision.of(decision, null));
            })
            .error(decisionDefer::_fail)
        ;
        return router;
    }

    @Override
    public Promise<T> then(final SuccessHandler<T> successHandler) {
        final SuccessHandler<T> _successHandler = successHandler == null ? emptySuccessHandler : successHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>(_successHandler, Type.SuccessHandler);
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                _successHandler.accept(value);
                _deferNext.complete(value);
            } catch (final Exception ex) {
                logError(ex);
                _deferNext._fail(ex);
            }
            return promise;
        } else if (isError()) {
            _deferNext._fail(error);
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
                promise._fail(error);
            } catch (final Exception ex) {
                logError(ex);
                error.addSuppressed(ex);
                promise._fail(error);
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
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                _completeHandler.accept(this);
                _deferNext.complete(value);
            } catch (final Exception ex) {
                logError(ex);
                _deferNext._fail(ex);
            }
            return promise;
        } else if (isError()) {
            try {
                _completeHandler.accept(this);
                _deferNext._fail(error);
            } catch (final Exception ex) {
                logError(ex);
                _deferNext._fail(ex);
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
        final Defer<Object> defer = Promises.defer();
        defer.promise()
//        Promises.from("sona")
            .then(s -> {
                System.out.println();
//                    throw new RuntimeException();
            })
            .complete(p -> {
                System.out.println();
            })
            .map(s -> Boolean.TRUE)
            .then(s -> {
                System.out.println(s);
                throw new RuntimeException();
            })
            .complete(p -> {
                System.out.println(p);
            })
            .complete(p -> {
                System.out.println(p);
            })
        ;

        defer.complete("sona");
    }

    public static void logError(Throwable error) {
        LOGGER.error("PROMISE_ERROR: ", error);
    }
}
