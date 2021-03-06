package io.crm.validator.impl.json.object;

import io.crm.ErrorCodes;
import io.crm.MessageBundle;
import io.crm.validator.ValidationResult;
import io.crm.validator.ValidationResultBuilder;
import io.crm.validator.Validator;
import io.vertx.core.json.JsonObject;

/**
 * Created by shahadat on 3/1/16.
 */
public class Contains implements Validator<JsonObject> {
    private final MessageBundle messageBundle;
    private final String field;

    public Contains(MessageBundle messageBundle, String field) {
        this.messageBundle = messageBundle;
        this.field = field;
    }

    @Override
    public ValidationResult validate(JsonObject json) {

        if (!json.containsKey(field)) {
            return invalidate(json);
        }
        return null;
    }

    private ValidationResult invalidate(JsonObject json) {
        return new ValidationResultBuilder()
            .setErrorCode(ErrorCodes.LENGTH_VALIDATION_ERROR.code())
            .setAdditionals(
                new JsonObject()
                    .put("field", field))
            .createValidationResult();
    }
}
