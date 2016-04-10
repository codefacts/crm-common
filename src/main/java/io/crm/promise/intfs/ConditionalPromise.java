package io.crm.promise.intfs;

import io.crm.intfs.ConsumerUnchecked;

/**
 * Created by someone on 09/11/2015.
 */
public interface ConditionalPromise<T> {
    ConditionalPromise<T> on(String condition, ConsumerUnchecked<T> promiseConsumer);

    ConditionalPromise<T> contnue(ConsumerUnchecked<T> promiseConsumer);

    ConditionalPromise<T> error(ErrorHandler errorHandler);

    ConditionalPromise<T> complete(CompleteHandler<T> completeHandler);
}
