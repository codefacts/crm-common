package io.crm.pipelines.validator.composer;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidatorPipeline;
import io.vertx.core.json.JsonObject;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by shahadat on 2/28/16.
 */
public class JsonObjectValidatorComposer {
    private final ValidatorPipeline<JsonObject> validatorPipeline;
    private final MessageBundle messageBundle;

    public JsonObjectValidatorComposer(ValidatorPipeline<JsonObject> validatorPipeline, MessageBundle messageBundle) {
        Objects.requireNonNull(validatorPipeline);
        Objects.requireNonNull(messageBundle);
        this.validatorPipeline = validatorPipeline;
        this.messageBundle = messageBundle;
    }

    public JsonObjectValidatorComposer field(String field, Consumer<FieldValidatorComposer> consumer) {
        consumer.accept(new FieldValidatorComposer(field, validatorPipeline, messageBundle));
        return this;
    }
}
