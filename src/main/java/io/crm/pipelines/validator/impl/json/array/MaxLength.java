package io.crm.pipelines.validator.impl.json.array;

import io.crm.FailureCodes;
import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationResult;
import io.crm.pipelines.validator.ValidationResultBuilder;
import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static java.util.Objects.requireNonNull;

/**
 * Created by shahadat on 3/1/16.
 */
public class MaxLength implements Validator<JsonArray> {
    private final MessageBundle messageBundle;
    private final int maxLength;

    public MaxLength(MessageBundle messageBundle, int maxLength) {
        this.maxLength = maxLength;
        requireNonNull(messageBundle);
        this.messageBundle = messageBundle;
    }

    @Override
    public ValidationResult validate(JsonArray json) {

        if (json.size() > maxLength) {
            return invalidate(json);
        }

        return null;
    }

    private ValidationResult invalidate(JsonArray json) {
        return new ValidationResultBuilder()
            .setErrorCode(FailureCodes.MAX_LENGTH_VALIDATION_ERROR.code())
            .setAdditionals(
                new JsonObject()
                    .put("maxLength", maxLength)
            )
            .createValidationResult();
    }
}
