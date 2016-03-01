package io.crm.pipelines.validator.impl;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationResult;
import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonObject;

import static java.util.Objects.requireNonNull;

/**
 * Created by shahadat on 2/28/16.
 */
public class StringValidator implements Validator<JsonObject> {
    private final TypeValidator typeValidator;

    public StringValidator(MessageBundle messageBundle, String field) {
        typeValidator = new TypeValidator(messageBundle, field,
            jsonObject -> jsonObject.getInteger(field),
            validationResult -> {
                validationResult.getAdditionals()
                    .put(TypeValidator.TYPE, Types.STRING);
                return validationResult;
            });
    }


    @Override
    public ValidationResult validate(JsonObject val) {
        return typeValidator.validate(val);
    }
}
