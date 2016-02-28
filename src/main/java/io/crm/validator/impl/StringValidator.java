package io.crm.validator.impl;

import io.crm.FailureCodes;
import io.crm.MessageBundle;
import io.crm.validator.ValidationResult;
import io.crm.validator.ValidationResultBuilder;
import io.crm.validator.Validator;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by shahadat on 2/28/16.
 */
public class StringValidator implements Validator<JsonObject> {
    private final MessageBundle messageBundle;
    private final String field;

    public StringValidator(MessageBundle messageBundle, String field) {
        requireNonNull(messageBundle);
        requireNonNull(field);
        this.messageBundle = messageBundle;
        this.field = field;
    }

    @Override
    public ValidationResult validate(JsonObject json) {
        try {
            json.getString(field);
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
