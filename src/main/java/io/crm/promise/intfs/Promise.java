package io.crm.promise.intfs;

import io.crm.intfs.ConsumerInterface;
import io.crm.intfs.FunctionAsync;
import io.crm.intfs.FunctionUnchecked;

/**
 * Created by someone on 15/10/2015.
 */
public interface Promise<T> {

    public <R> Promise<R> map(FunctionUnchecked<T, R> functionUnchecked);

    public <R> Promise<R> mapPromise(FunctionAsync<T, R> function);

    public Promise<Void> then(ConsumerInterface<T> valueConsumer);

    public Promise<T> success(ConsumerInterface<T> successHandler);

    public Promise<T> error(ConsumerInterface<Throwable> errorHandler);

    public Promise<T> complete(ConsumerInterface<Promise<T>> completeHandler);

    public boolean isComplete();

    public boolean isSuccess();

    public boolean isError();

    public T get();

    public T getOrElse(T t);

    public Throwable error();
}
