package io.crm.validator;

import io.crm.ErrorCodes;
import io.crm.MessageBundle;
import io.vertx.core.json.JsonObject;

import static java.util.Objects.requireNonNull;

/**
 * Created by shahadat on 4/3/16.
 */
public class NonZeroValidator implements Validator<JsonObject> {
    private final MessageBundle messageBundle;
    private final String field;

    public NonZeroValidator(MessageBundle messageBundle, String field) {
        requireNonNull(messageBundle);
        requireNonNull(field);
        this.messageBundle = messageBundle;
        this.field = field;
    }

    @Override
    public ValidationResult validate(JsonObject json) {

        Long aLong = json.getLong(field);

        if (aLong != null) {
            long val = aLong;
            if (val == 0) {
                invalidate(json);
            }
        }

        return null;
    }

    private ValidationResult invalidate(JsonObject json) {
        return new ValidationResultBuilder()
            .setField(field)
            .setValue(json.getValue(field))
            .setErrorCode(ErrorCodes.NON_ZERO_NUMBER_VALIDATION_ERROR.code())
            .createValidationResult();
    }
}
