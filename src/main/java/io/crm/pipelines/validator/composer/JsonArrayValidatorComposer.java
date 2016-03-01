package io.crm.pipelines.validator.composer;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidatorPipeline;
import io.vertx.core.json.JsonObject;

/**
 * Created by shahadat on 3/1/16.
 */
public class JsonArrayValidatorComposer {
    private final ValidatorPipeline<JsonObject> validatorPipeline;
    private final MessageBundle messageBundle;

    public JsonArrayValidatorComposer(ValidatorPipeline<JsonObject> validatorPipeline, MessageBundle messageBundle) {
        this.validatorPipeline = validatorPipeline;
        this.messageBundle = messageBundle;
    }


}
