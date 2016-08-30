package io.crm.promise.intfs;

import io.crm.intfs.FunctionUnchecked;
import io.crm.intfs.PredicateUnchecked;

import java.util.Collection;
import java.util.List;

/**
 * Created by someone on 17/11/2015.
 */
public interface Stream<T> {
    public <R> Stream<R> map(MapHandler<T, R> functionUnchecked);

    public <R> Stream<R> mapToStream(MapToStreamHandler<T, R> function);

    public <R> ConditionalStream<R> mapAndDecide(MapAndDecideHandler<T, R> functionUnchecked);

    public <R> ConditionalStream<R> mapToStreamAndDecide(MapToStreamAndDecideHandler<T, R> function);

    public ConditionalStream<Void> thenDecide(ThenDecideHandler<T> valueConsumer);

    public Stream<T> then(SuccessHandler<T> successHandler);

    public Stream<T> error(ErrorHandler errorHandler);

    public Stream<T> complete(CompleteHandler<T> completeHandler);

    public <R> Stream<R> split();

    public <R> Stream<R> split(FunctionUnchecked<T, Collection<R>> spliterator);

    public <R> Stream<R> aggregate(FunctionUnchecked<Collection<T>, R> spliterator);

    public <R> Stream<R> flatMap(FunctionUnchecked<? super T, ? extends Stream<? extends R>> mapper);

    public Stream<List<T>> buffer(int size, long time, java.util.concurrent.TimeUnit unit);

    public Stream<T> filter(PredicateUnchecked<? super T> predicate);
}
