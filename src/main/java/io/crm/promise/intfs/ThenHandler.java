package io.crm.promise.intfs;

import io.crm.intfs.ConsumerUnchecked;

/**
 * Created by someone on 16/10/2015.
 */
public interface ThenHandler<T> extends ConsumerUnchecked<T>, Invokable {
}
