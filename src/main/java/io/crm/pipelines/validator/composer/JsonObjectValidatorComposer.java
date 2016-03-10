package io.crm.pipelines.validator.composer;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationPipeline;
import io.vertx.core.json.JsonObject;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by shahadat on 2/28/16.
 */
public class JsonObjectValidatorComposer {
    private final ValidationPipeline<JsonObject> validationPipeline;
    private final MessageBundle messageBundle;

    public JsonObjectValidatorComposer(ValidationPipeline<JsonObject> validationPipeline, MessageBundle messageBundle) {
        Objects.requireNonNull(validationPipeline);
        Objects.requireNonNull(messageBundle);
        this.validationPipeline = validationPipeline;
        this.messageBundle = messageBundle;
    }

    public JsonObjectValidatorComposer field(String field, Consumer<FieldValidatorComposer> consumer) {
        consumer.accept(new FieldValidatorComposer(field, validationPipeline, messageBundle));
        return this;
    }

    public ValidationPipeline<JsonObject> getValidationPipeline() {
        return validationPipeline;
    }
}
