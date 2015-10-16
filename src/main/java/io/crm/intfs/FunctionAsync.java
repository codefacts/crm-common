package io.crm.intfs;

import io.crm.promise.intfs.Promise;

/**
 * Created by someone on 16/10/2015.
 */
public interface FunctionAsync<T, R> {
    public Promise<R> apply(T value) throws Exception;
}
