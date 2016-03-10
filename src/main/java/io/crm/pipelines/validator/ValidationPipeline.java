package io.crm.pipelines.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahadat on 2/28/16.
 */
public class ValidationPipeline<T> {

    private final List<Validator<T>> validatorList = new ArrayList<>();

    public ValidationPipeline add(Validator<T> validator) {
        validatorList.add(validator);
        return this;
    }

    public ValidationPipeline remove(Validator validator) {
        validatorList.remove(validator);
        return this;
    }

    public ValidationPipeline clear() {
        validatorList.clear();
        return this;
    }

    public List<ValidationResult> validate(T obj) {
        return validatorList.stream().reduce(new ArrayList<>(),
            (validationResults, validator) -> {
                ValidationResult validationResult = validator.validate(obj);
                if (validationResult != null) validationResults.add(validationResult);
                return validationResults;
            }, (u, u2) -> {
                u.addAll(u2);
                return u;
            });
    }
}
