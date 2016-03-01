package io.crm.pipelines.validator.impl;

import io.crm.FailureCodes;
import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationResult;
import io.crm.pipelines.validator.ValidationResultBuilder;
import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonObject;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by shahadat on 3/1/16.
 */
public class TypeValidator implements Validator<JsonObject> {
    public static final String TYPE = "type";
    private final MessageBundle messageBundle;
    private final String field;
    private final Consumer<JsonObject> check;
    private final Function<ValidationResult, ValidationResult> onValidate;

    public TypeValidator(MessageBundle messageBundle, String field, Consumer<JsonObject> check, Function<ValidationResult, ValidationResult> onValidate) {
        this.messageBundle = messageBundle;
        this.field = field;
        this.check = check;
        this.onValidate = onValidate;
    }

    @Override
    public ValidationResult validate(JsonObject json) {
        try {
            check.accept(json);
        } catch (Exception e) {
            return invalidate(json);
        }
        return null;
    }

    private ValidationResult invalidate(JsonObject json) {
        return onValidate.apply(new ValidationResultBuilder()
            .setField(field)
            .setValue(json.getValue(field))
            .setErrorCode(FailureCodes.INVALID_TYPE_VALIDATION_ERROR.code())
            .createValidationResult());
    }
}
