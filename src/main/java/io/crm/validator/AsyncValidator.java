package io.crm.validator;

import io.crm.promise.intfs.Promise;

/**
 * Created by shahadat on 2/28/16.
 */
public interface AsyncValidator<T> {
    Promise<ValidationResult> validate(T val);
}
