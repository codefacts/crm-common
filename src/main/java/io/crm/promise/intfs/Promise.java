package io.crm.promise.intfs;

/**
 * Created by someone on 15/10/2015.
 */
public interface Promise<T> {

    public <R> Promise<R> map(MapToHandler<T, R> functionUnchecked);

    public <R> Promise<R> mapToPromise(MapToPromiseHandler<T, R> function);

    public <R> ConditionalPromise<R> mapAndDecide(MapAndDecideHandler<T, R> functionUnchecked);

    public <R> ConditionalPromise<R> mapToPromiseAndDecide(MapToPromiseAndDecideHandler<T, R> function);

    public ConditionalPromise<Void> thenDecide(ThenDecideHandler<T> valueConsumer);

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
