package io.crm.promise.intfs;

/**
 * Created by someone on 15/10/2015.
 */
public interface Promise<T> {

    Promise<T> filter(FilterHandler<T> predicateUnchecked);

    <R> Promise<R> map(MapHandler<T, R> functionUnchecked);

    <R> Promise<R> mapP(MapPHandler<T, R> function);

    Promise<T> then(SuccessHandler<T> successHandler);
    Promise<T> thenP(SuccessHandler<T> successHandler);

    Promise<T> error(ErrorHandler errorHandler);
    Promise<T> errorP(ErrorHandler errorHandler);

    Promise<T> complete(CompleteHandler<T> completeHandler);
    Promise<T> completeP(CompleteHandler<T> completeHandler);

    boolean isComplete();

    boolean isSuccess();

    boolean isError();

    T get();

    T orElse(T t);

    Throwable error();
}
