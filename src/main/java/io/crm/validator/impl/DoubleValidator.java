package io.crm.validator.impl;

import io.crm.FailureCodes;
import io.crm.MessageBundle;
import io.crm.validator.ValidationResult;
import io.crm.validator.ValidationResultBuilder;
import io.crm.validator.Validator;
import io.vertx.core.json.JsonObject;

/**
 * Created by shahadat on 2/28/16.
 */
public class DoubleValidator implements Validator<JsonObject> {
    private final MessageBundle messageBundle;
    private final String field;

    public DoubleValidator(MessageBundle messageBundle, String field) {
        this.messageBundle = messageBundle;
        this.field = field;
    }

    @Override
    public ValidationResult validate(JsonObject json) {
        try {
            json.getDouble(field);
        } catch (Exception e) {
            return invalidate(json);
        }
        return null;
    }

    private ValidationResult invalidate(JsonObject json) {
        return new ValidationResultBuilder()
            .setField(field)
            .setValue(json.getValue(field))
            .setErrorCode(FailureCodes.INVALID_TYPE_VALIDATION_ERROR.code())
            .createValidationResult();
    }
}
