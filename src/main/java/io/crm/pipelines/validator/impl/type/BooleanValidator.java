package io.crm.pipelines.validator.impl.type;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationResult;
import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonObject;

/**
 * Created by shahadat on 2/28/16.
 */
public class BooleanValidator implements Validator<JsonObject> {
    private final TypeValidator typeValidator;

    public BooleanValidator(MessageBundle messageBundle, String field) {
        typeValidator = new TypeValidator(messageBundle, field,
            jsonObject -> jsonObject.getBoolean(field),
            validationResult -> {
                validationResult.getAdditionals()
                    .put(TypeValidator.TYPE, Types.BOOLEAN);
                return validationResult;
            });
    }


    @Override
    public ValidationResult validate(JsonObject val) {
        return typeValidator.validate(val);
    }
}
