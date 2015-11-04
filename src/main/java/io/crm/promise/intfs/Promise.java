package io.crm.promise.intfs;

import io.crm.intfs.FunctionUnchecked;

/**
 * Created by someone on 15/10/2015.
 */
public interface Promise<T> {

    public <R> Promise<R> mapTo(MapHandler<T, R> functionUnchecked);

    public <R> Promise<R> mapToPromise(MapPromiseHandler<T, R> function);

    public Promise<Void> mapToVoid(ThenHandler<T> valueConsumer);

    public Promise<T> then(SuccessHandler<T> successHandler);

    public Promise<T> error(ErrorHandler errorHandler);

    public Promise<T> complete(CompleteHandler<T> completeHandler);

    public boolean isComplete();

    public boolean isSuccess();

    public boolean isError();

    public T get();

    public T getOrElse(T t);

    public Throwable error();
}
