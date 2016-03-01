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
public class SequenceEqual implements Validator<JsonArray> {
    private final MessageBundle messageBundle;
    private final JsonArray jsonArray;

    public SequenceEqual(MessageBundle messageBundle, JsonArray jsonArray) {
        requireNonNull(jsonArray);
        this.jsonArray = jsonArray;
        requireNonNull(messageBundle);
        this.messageBundle = messageBundle;
    }

    @Override
    public ValidationResult validate(JsonArray json) {

        for (int i = 0; i < json.size(); i++) {
            boolean equals = json.getValue(i).equals(jsonArray.getValue(i));
            if (!equals) {
                return invalidate(json);
            }
        }

        return null;
    }

    private ValidationResult invalidate(JsonArray json) {
        return new ValidationResultBuilder()
            .setErrorCode(FailureCodes.INVALID_SEQUENCE_ORDER_VALIDATION_ERROR.code())
            .setAdditionals(
                new JsonObject()
                    .put("jsonArray", jsonArray)
            )
            .createValidationResult();
    }
}
