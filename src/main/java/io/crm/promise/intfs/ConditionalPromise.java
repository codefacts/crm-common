package io.crm.promise.intfs;

import io.crm.intfs.ConsumerUnchecked;

/**
 * Created by someone on 09/11/2015.
 */
public interface ConditionalPromise<T> {
    public ConditionalPromise<T> on(String condition, ConsumerUnchecked<Promise<T>> promiseConsumer);

    public ConditionalPromise<T> otherwise(ConsumerUnchecked<Promise<T>> promiseConsumer);

    public ConditionalPromise<T> error(ErrorHandler errorHandler);

    public ConditionalPromise<T> complete(CompleteHandler<T> completeHandler);
}
