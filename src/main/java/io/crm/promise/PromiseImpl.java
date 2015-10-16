package io.crm.promise;

import io.crm.promise.intfs.*;

/**
 * Created by someone on 15/10/2015.
 */
final public class PromiseImpl<T> implements Promise<T>, Defer<T> {
    private T value;
    private Throwable error;
    private State state;

    private Object invokeNext;
    private Defer deferNext;

    @Override
    public void fail(Throwable throwable) {
        error = throwable;
        state = State.error;
        invokeErrorNext();
    }

    private void invokeErrorNext() {

        if (invokeNext instanceof SuccessHandler) {

        } else if (invokeNext instanceof ErrorHandler) {

        } else if (invokeNext instanceof CompleteHandler) {

        } else if (invokeNext instanceof MapHandler) {

        } else if (invokeNext instanceof MapPromiseHandler) {

        } else {

        }
    }

    @Override
    public void complete() {
        state = State.success;
        invokeSuccessNext();
    }

    private void invokeSuccessNext() {

    }

    @Override
    public void complete(T value) {
        this.value = value;
        state = State.success;
        invokeSuccessNext();
    }

    @Override
    public <R> Promise<R> map(MapHandler<T, R> functionInt) {
        return null;
    }

    @Override
    public <R> Promise<R> mapPromise(MapPromiseHandler<T, R> function) {
        return null;
    }

    @Override
    public Promise<Void> then(ThenHandler<T> valueConsumer) {
        return null;
    }

    @Override
    public Promise<T> success(SuccessHandler<T> successHandler) {
        return null;
    }

    @Override
    public Promise<T> error(ErrorHandler<Throwable> errorHandler) {
        return null;
    }

    @Override
    public Promise<T> complete(CompleteHandler<Promise<T>> completeHandler) {
        return null;
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

    public enum State {success, error}
}
