package io.crm.validator;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Promise;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahadat on 2/28/16.
 */
public class ValidatorPipeline {

    private final List<Validator> validatorList = new ArrayList<>();

    public ValidatorPipeline add(Validator validator) {
        validatorList.add(validator);
        return this;
    }

    public ValidatorPipeline remove(Validator validator) {
        validatorList.remove(validator);
        return this;
    }

    public ValidatorPipeline clear() {
        validatorList.clear();
        return this;
    }

    public List<ValidationResult> validate(Object obj) {
        return validatorList.stream().reduce(new ArrayList<ValidationResult>(), (validationResults, validator) -> {
            ValidationResult validationResult = validator.validate(obj);
            if (validationResult != null) validationResults.add(validationResult);
            return validationResults;
        }, (u, u2) -> {
            u.addAll(u2);
            return u;
        });
    }
}
