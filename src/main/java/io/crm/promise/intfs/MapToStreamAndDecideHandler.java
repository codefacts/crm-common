package io.crm.promise.intfs;

import io.crm.intfs.FunctionUnchecked;
import io.crm.promise.Decision;

/**
 * Created by someone on 17/11/2015.
 */
public interface MapToStreamAndDecideHandler<T, R> extends FunctionUnchecked<T, Decision<Stream<R>>>, Invokable {
}
