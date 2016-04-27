package io.crm.pipelines.transformation;

import io.crm.util.Context;
import io.crm.promise.intfs.Promise;

/**
 * Created by shahadat on 4/27/16.
 */
public interface TransformDeferred<T, R> {
    Promise<R> transform(T val, Context context);
}
