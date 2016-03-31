package io.crm.pipelines.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahadat on 2/28/16.
 */
public class ValidationPipeline<T> {

    private final List<Validator<T>> validatorList;

    public ValidationPipeline(List<Validator<T>> validatorList) {
        this.validatorList = validatorList;
    }

    public List<ValidationResult> validate(T obj) {
        final ArrayList<ValidationResult> list = validatorList.stream().reduce(new ArrayList<>(),
            (validationResults, validator) -> {
                ValidationResult validationResult = validator.validate(obj);
                if (validationResult != null) validationResults.add(validationResult);
                return validationResults;
            }, (u, u2) -> {
                u.addAll(u2);
                return u;
            });
        return list.size() <= 0 ? null : list;
    }
}
