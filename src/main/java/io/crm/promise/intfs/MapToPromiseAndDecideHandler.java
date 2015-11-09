package io.crm.promise.intfs;

import io.crm.intfs.FunctionUnchecked;
import io.crm.promise.Decision;
import io.crm.util.touple.MutableTpl2;

/**
 * Created by someone on 08/11/2015.
 */
public interface MapToPromiseAndDecideHandler<T, R> extends FunctionUnchecked<T, Decision<Promise<R>>>, Invokable {
}
