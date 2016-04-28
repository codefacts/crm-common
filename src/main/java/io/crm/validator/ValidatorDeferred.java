package io.crm.validator;

import io.crm.util.Context;
import io.crm.promise.intfs.Promise;

/**
 * Created by shahadat on 4/27/16.
 */
public interface ValidatorDeferred<T> {
    Promise<ValidationResult> validate(T val, Context context);
}
