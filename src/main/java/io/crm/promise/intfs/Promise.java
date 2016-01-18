package io.crm.promise.intfs;

/**
 * Created by someone on 15/10/2015.
 */
public interface Promise<T> {

    <R> Promise<R> map(MapToHandler<T, R> functionUnchecked);

    <R> Promise<R> mapToPromise(MapToPromiseHandler<T, R> function);

    <R> ConditionalPromise<R> decideAndMap(MapAndDecideHandler<T, R> functionUnchecked);

    <R> ConditionalPromise<R> decideAndMapToPromise(MapToPromiseAndDecideHandler<T, R> function);

    ConditionalPromise<Void> decide(ThenDecideHandler<T> valueConsumer);

    Promise<T> then(SuccessHandler<T> successHandler);

    Promise<T> error(ErrorHandler errorHandler);

    Promise<T> complete(CompleteHandler<T> completeHandler);

    boolean isComplete();

    boolean isSuccess();

    boolean isError();

    T get();

    T getOrElse(T t);

    Throwable error();
}
