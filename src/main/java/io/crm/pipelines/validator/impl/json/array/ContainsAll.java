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
public class ContainsAll implements Validator<JsonArray> {
    private final MessageBundle messageBundle;
    private final JsonArray jsonArray;

    public ContainsAll(MessageBundle messageBundle, JsonArray jsonArray) {
        requireNonNull(jsonArray);
        this.jsonArray = jsonArray;
        requireNonNull(messageBundle);
        this.messageBundle = messageBundle;
    }

    @Override
    public ValidationResult validate(JsonArray json) {

        if (!json.getList().containsAll(jsonArray.getList())) {
            return invalidate(json);
        }

        return null;
    }

    private ValidationResult invalidate(JsonArray json) {
        return new ValidationResultBuilder()
            .setErrorCode(FailureCodes.VALUE_MISSING_VALIDATION_ERROR.code())
            .setAdditionals(
                new JsonObject()
                    .put("jsonArray", jsonArray)
            )
            .createValidationResult();
    }
}
