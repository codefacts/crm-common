package io.crm.promise.intfs;

import io.crm.intfs.FunctionAsync;

/**
 * Created by someone on 16/10/2015.
 */
public interface MapToPromiseHandler<T, R> extends FunctionAsync<T, R>, Invokable {
}
