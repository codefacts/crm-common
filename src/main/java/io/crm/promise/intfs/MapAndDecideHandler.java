package io.crm.promise.intfs;

import io.crm.intfs.FunctionUnchecked;
import io.crm.promise.Decision;

/**
 * Created by someone on 08/11/2015.
 */
public interface MapAndDecideHandler<T, R> extends FunctionUnchecked<T, Decision<R>>, Invokable {
}
