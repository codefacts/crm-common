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
    private static final MapHandler EMPTY_MAP_TO_HANDLER = s -> s;
    private static final MapPHandler EMPTY_MAP_TO_PROMISE_HANDLER = s -> Promises.from(s);
    private static final FilterHandler emptyFilterHandler = s -> true;
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
        _fail(throwable);
        logError(throwable);
    }

    private void _fail(final Throwable ex) {
        error = ex;
        setState(State.ERROR_AND_COMPLETE);
        invokeErrorCallback(nextPromise, this);
    }

    @Override
    public void complete() {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        setState(State.SUCCESS_AND_COMPLETE);
        invokeSuccessCallback(nextPromise, this);
    }

    @Override
    public void complete(final T value) {
        if (isComplete()) {
            throw new PromiseAlreadyComplete("Promise already complete. " + toString());
        }
        this.value = value;
        setState(State.SUCCESS_AND_COMPLETE);
        invokeSuccessCallback(nextPromise, this);
    }

    private void completeWithoutValue() {
        setState(State.COMPLETE_ONLY);
        invokeCompleteOnlyCallback(nextPromise, this);
    }

    private void invokeCompleteOnlyCallback(PromiseImpl<T> nextPromise, PromiseImpl $this) {
        for (; ; ) {
            if (nextPromise == null) {
                return;
            }
            try {
                final Invokable _invokeNext = nextPromise.callback;
                if (_invokeNext instanceof CompleteHandler) {
                    ((CompleteHandler) _invokeNext).accept($this);
                }
            } catch (final Exception ex) {
                logError(ex);
                nextPromise._fail(ex);
                return;
            }

            nextPromise.setState(State.COMPLETE_ONLY);
            $this = nextPromise;
            nextPromise = nextPromise.nextPromise;
        }
    }

    @Override
    public Promise<T> promise() {
        return this;
    }

    private void invokeErrorCallback(PromiseImpl<T> nextPromise, PromiseImpl $this) {
        Throwable error = $this.error;
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
            nextPromise.setState(State.ERROR_AND_COMPLETE);
            $this = nextPromise;
            error = nextPromise.error;
            nextPromise = nextPromise.nextPromise;
        }
    }

    private void invokeSuccessCallback(PromiseImpl nextPromise, PromiseImpl $this) {
        Object value = $this.value;
        for (; ; ) {
            if (nextPromise == null) return;
            try {

                final Invokable _invokeNext = nextPromise.callback;

                if (nextPromise.type == Type.MapTo) {

                    final Object retValue = ((MapHandler) _invokeNext).apply(value);
                    value = nextPromise.value = retValue;

                } else if (_invokeNext instanceof MapPHandler) {

                    final Promise promise = ((MapPHandler) _invokeNext).apply(value);

                    if (promise == null) {
                        throw new NullPointerException("No Promise was returned from the mapP " +
                            "Handler for value: " + value);
                    }

                    final PromiseImpl pp = nextPromise;
                    promise
                        .error(e ->
                            pp._fail(e))
                        .then(s ->
                            pp.complete(s));

                    return;

                } else if (_invokeNext instanceof SuccessHandler) {

                    ((SuccessHandler) _invokeNext).accept(value);
                    value = nextPromise.value = value;

                } else if (_invokeNext instanceof CompleteHandler) {

                    ((CompleteHandler) _invokeNext).accept($this);
                    value = nextPromise.value = value;

                } else if (_invokeNext instanceof FilterHandler) {
                    boolean test = ((FilterHandler) _invokeNext).test(value);
                    if (test) {
                        nextPromise.value = value;
                    } else {
                        nextPromise.completeWithoutValue();
                        return;
                    }
                } else {
                    nextPromise.value = value;
                }

                nextPromise.setState(State.SUCCESS_AND_COMPLETE);

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
    public Promise<T> filter(FilterHandler<T> filterHandler) {
        final FilterHandler<T> _filterHandler = filterHandler == null ? emptyFilterHandler : filterHandler;
        final PromiseImpl<T> promise = new PromiseImpl<>(_filterHandler, Type.FilterHandler);
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                boolean test = _filterHandler.test(value);
                if (test) {
                    _deferNext.complete(value);
                } else {
                    _deferNext.completeWithoutValue();
                }
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
    public <R> Promise<R> map(final MapHandler<T, R> mapHandler) {
        final MapHandler<T, R> _mapHandler = mapHandler == null ? EMPTY_MAP_TO_HANDLER : mapHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>(_mapHandler, Type.MapTo);
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                if (isCompleteOnly()) {
                    _deferNext.completeWithoutValue();
                } else {
                    final R retVal = _mapHandler.apply(value);
                    _deferNext.complete(retVal);
                }
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
    public <R> Promise<R> mapP(final MapPHandler<T, R> promiseHandler) {
        final MapPHandler<T, R> _promiseHandler = promiseHandler == null ? EMPTY_MAP_TO_PROMISE_HANDLER : promiseHandler;
        final PromiseImpl<R> promise = new PromiseImpl<>(_promiseHandler, Type.MapToPromise);
        final PromiseImpl _deferNext = nextPromise = promise;
        if (isSuccess()) {
            try {
                final Promise<R> rPromise = _promiseHandler.apply(value);
                if (rPromise == null) {
                    throw new NullPointerException("No Promise was returned from the mapP Handler for value: " + value);
                }
                rPromise.complete(p -> {
                    if (((PromiseImpl) p).isCompleteOnly()) {
                        _deferNext.completeWithoutValue();
                    } else if (p.isSuccess()) {
                        _deferNext.complete(p.get());
                    } else {
                        _deferNext._fail(p.error());
                    }
                });
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
            .complete(p -> {
                if (((PromiseImpl) p).isCompleteOnly()) {
                    decisionDefer.completeWithoutValue();
                } else if (p.isSuccess()) {
                    decisionDefer.complete(p.get());
                } else {
                    decisionDefer._fail(p.error());
                }
            })
        ;
        return router;
    }

    @Override
    public <R> ConditionalPromise<R> decideAndMapToPromise(MapToPromiseAndDecideHandler<T, R> function) {
        final PromiseImpl<Decision<R>> decisionDefer = new PromiseImpl<>(null, null);
        final ConditionalPromiseImpl<R> router = new ConditionalPromiseImpl<>(decisionDefer.promise());
        this.map(function::apply)
            .then(decision -> decision.retVal
                .complete(p -> {
                    if (((PromiseImpl) p).isCompleteOnly()) {
                        decisionDefer.completeWithoutValue();
                    } else if (p.isSuccess()) {
                        decisionDefer.complete(Decision.of(decision.decision, p.get()));
                    } else {
                        decisionDefer._fail(p.error());
                    }
                }))
        ;
        return router;
    }

    @Override
    public ConditionalPromise<T> decide(ThenDecideHandler<T> valueConsumer) {
        final PromiseImpl<Decision<T>> decisionDefer = new PromiseImpl<>(null, null);
        final ConditionalPromiseImpl<T> router = new ConditionalPromiseImpl<>(decisionDefer.promise());
        this.map(valueConsumer::apply)
            .complete(p -> {
                if (((PromiseImpl) p).isCompleteOnly()) {
                    decisionDefer.completeWithoutValue();
                } else if (p.isSuccess()) {
                    decisionDefer.complete(Decision.of(p.get(), this.value));
                } else {
                    decisionDefer._fail(p.error());
                }
            })
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
                if (isCompleteOnly()) {
                    _deferNext.completeWithoutValue();
                } else {
                    _successHandler.accept(value);
                    _deferNext.complete(value);
                }
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
        } else if (isCompleteOnly()) {
            promise.completeWithoutValue();
        } else if (isSuccess()) {
            promise.complete(value);
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
                if (isCompleteOnly()) {
                    _deferNext.completeWithoutValue();
                } else {
                    _deferNext.complete(value);
                }
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
        return (getState() == State.ERROR_AND_COMPLETE)
            | (getState() == State.SUCCESS_AND_COMPLETE)
            | (getState() == State.COMPLETE_ONLY);
    }

    public boolean isCompleteOnly() {
        return getState() == State.COMPLETE_ONLY;
    }

    @Override
    public boolean isSuccess() {
        return (getState() == State.SUCCESS_AND_COMPLETE) | (getState() == State.COMPLETE_ONLY);
    }

    @Override
    public boolean isError() {
        return getState() == State.ERROR_AND_COMPLETE;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T t) {
        return value == null ? t : value;
    }

    @Override
    public Throwable error() {
        return error;
    }

    private State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    private enum State {SUCCESS_AND_COMPLETE, ERROR_AND_COMPLETE, COMPLETE_ONLY}

    private enum Type {
        MapTo,
        MapToPromise,
        MapToVoid,
        SuccessHandler,
        ErrorHandler,
        FilterHandler,
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
