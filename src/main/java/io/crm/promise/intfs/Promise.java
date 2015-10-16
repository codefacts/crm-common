package io.crm.promise.intfs;

import io.crm.intfs.ConsumerInterface;
import io.crm.intfs.FunctionInt;

/**
 * Created by someone on 15/10/2015.
 */
public interface Promise<T> {

    public <R> Promise<R> map(MapHandler<T, R> functionInt);

    public <R> Promise<R> mapPromise(MapPromiseHandler<T, R> function);

    public Promise<Void> then(ThenHandler<T> valueConsumer);

    public Promise<T> success(SuccessHandler<T> successHandler);

    public Promise<T> error(ErrorHandler<Throwable> errorHandler);

    public Promise<T> complete(CompleteHandler<Promise<T>> completeHandler);

    public boolean isComplete();

    public boolean isSuccess();

    public boolean isError();

    public T get();

    public T getOrElse(T t);

    public Throwable error();
}
