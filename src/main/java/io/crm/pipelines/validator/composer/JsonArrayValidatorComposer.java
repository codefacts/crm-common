package io.crm.pipelines.validator.composer;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationPipeline;
import io.vertx.core.json.JsonObject;

/**
 * Created by shahadat on 3/1/16.
 */
public class JsonArrayValidatorComposer {
    private final ValidationPipeline<JsonObject> validationPipeline;
    private final MessageBundle messageBundle;

    public JsonArrayValidatorComposer(ValidationPipeline<JsonObject> validationPipeline, MessageBundle messageBundle) {
        this.validationPipeline = validationPipeline;
        this.messageBundle = messageBundle;
    }


}
