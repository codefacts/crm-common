package io.crm.pipelines.validator.impl;

import io.crm.FailureCodes;
import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationResult;
import io.crm.pipelines.validator.ValidationResultBuilder;
import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonObject;

/**
 * Created by shahadat on 2/28/16.
 */
public class NotNullEmptyOrWhiteSpace implements Validator<JsonObject> {
    private final MessageBundle messageBundle;
    private final String field;

    public NotNullEmptyOrWhiteSpace(MessageBundle messageBundle, String field) {
        this.messageBundle = messageBundle;
        this.field = field;
    }

    @Override
    public ValidationResult validate(JsonObject json) {
        String string = json.getString(field);
        if (string == null)
            return invalidate(json);
        else if (string.trim().isEmpty())
            return invalidate(json);
        else return null;
    }

    private ValidationResult invalidate(JsonObject json) {
        return new ValidationResultBuilder()
            .setField(field)
            .setValue(json.getValue(field))
            .setErrorCode(FailureCodes.INVALID_VALUE_VALIDATION_ERROR.code())
            .createValidationResult();
    }
}