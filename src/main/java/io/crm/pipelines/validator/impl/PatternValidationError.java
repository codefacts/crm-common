package io.crm.pipelines.validator.impl;

import io.crm.FailureCodes;
import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationResult;
import io.crm.pipelines.validator.ValidationResultBuilder;
import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * Created by shahadat on 2/28/16.
 */
public class PatternValidationError implements Validator<JsonObject> {
    private final MessageBundle messageBundle;
    private final String field;
    private final Pattern pattern;
    private final int validationErrorCode;

    public PatternValidationError(MessageBundle messageBundle, String field, Pattern pattern) {
        this.pattern = pattern;
        requireNonNull(messageBundle);
        requireNonNull(field);
        this.messageBundle = messageBundle;
        this.field = field;
        validationErrorCode = FailureCodes.PATTERN_VALIDATION_ERROR.code();
    }

    public PatternValidationError(MessageBundle messageBundle, String field, Pattern pattern, int validationErrorCode) {
        this.pattern = pattern;
        requireNonNull(messageBundle);
        requireNonNull(field);
        this.messageBundle = messageBundle;
        this.field = field;
        this.validationErrorCode = validationErrorCode;
    }

    @Override
    public ValidationResult validate(JsonObject json) {
        String string = json.getString(field);
        if (string != null) {
            Matcher matcher = pattern.matcher(json.getString(field));
            if (!matcher.matches()) {
                invalidate(json);
            }
        }
        return null;
    }

    private ValidationResult invalidate(JsonObject json) {
        return new ValidationResultBuilder()
            .setField(field)
            .setValue(json.getValue(field))
            .setErrorCode(validationErrorCode)
            .setAdditionals(
                new JsonObject()
                    .put("pattern", pattern))
            .createValidationResult();
    }
}
