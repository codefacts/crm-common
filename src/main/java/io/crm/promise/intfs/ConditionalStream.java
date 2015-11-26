package io.crm.promise.intfs;

import io.crm.intfs.ConsumerUnchecked;

/**
 * Created by someone on 17/11/2015.
 */
public interface ConditionalStream<T> {
    public ConditionalStream<T> on(String condition, ConsumerUnchecked<Stream<T>> streamConsumerUnchecked);

    public ConditionalStream<T> otherwise(ConsumerUnchecked<Stream<T>> streamConsumerUnchecked);

    public ConditionalStream<T> error(ErrorHandler errorHandler);

    public ConditionalStream<T> complete(CompleteHandler<T> completeHandler);
}
