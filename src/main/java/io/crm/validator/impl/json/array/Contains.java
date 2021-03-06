package io.crm.validator.impl.json.array;

import io.crm.ErrorCodes;
import io.crm.MessageBundle;
import io.crm.validator.ValidationResult;
import io.crm.validator.ValidationResultBuilder;
import io.crm.validator.Validator;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static java.util.Objects.requireNonNull;

/**
 * Created by shahadat on 3/1/16.
 */
public class Contains<T> implements Validator<JsonArray> {
    private final MessageBundle messageBundle;
    private final T value;

    public Contains(MessageBundle messageBundle, T value) {
        this.value = value;
        requireNonNull(messageBundle);
        this.messageBundle = messageBundle;
    }

    @Override
    public ValidationResult validate(JsonArray json) {

        if (!json.contains(value)) {
            return invalidate(json);
        }

        return null;
    }

    private ValidationResult invalidate(JsonArray json) {
        return new ValidationResultBuilder()
            .setErrorCode(ErrorCodes.VALUE_MISSING_VALIDATION_ERROR.code())
            .setAdditionals(
                new JsonObject()
                    .put("element", value)
            )
            .createValidationResult();
    }
}
