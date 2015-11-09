package io.crm.promise.intfs;

import io.crm.intfs.ConsumerUnchecked;

/**
 * Created by someone on 09/11/2015.
 */
public interface Router<T> {
    public Router<T> on(String condition, ConsumerUnchecked<Promise<T>> promiseConsumer);

    public Router<T> otherwise(ConsumerUnchecked<Promise<T>> promiseConsumer);
}
