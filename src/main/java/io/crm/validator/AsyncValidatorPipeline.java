package io.crm.validator;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Promise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahadat on 2/28/16.
 */
public class AsyncValidatorPipeline {

    private final List<AsyncValidator> validatorList = new ArrayList<>();

    public AsyncValidatorPipeline add(AsyncValidator asyncValidator) {
        validatorList.add(asyncValidator);
        return this;
    }

    public AsyncValidatorPipeline remove(AsyncValidator asyncValidator) {
        validatorList.remove(asyncValidator);
        return this;
    }

    public AsyncValidatorPipeline clear() {
        validatorList.clear();
        return this;
    }

    public Promise<List<ValidationResult>> validate(Object obj) {
        ArrayList<Promise<ValidationResult>> promiseArrayList = validatorList.stream().reduce(new ArrayList<>(), (promises, asyncValidator) -> {
            promises.add(asyncValidator.validate(obj));
            return promises;
        }, (promises1, promises2) -> {
            promises1.addAll(promises2);
            return promises1;
        });
        return Promises.when(promiseArrayList);
    }
}
