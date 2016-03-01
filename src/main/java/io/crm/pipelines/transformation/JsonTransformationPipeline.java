package io.crm.pipelines.transformation;

import io.crm.pipelines.validator.Validator;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahadat on 3/1/16.
 */
public class JsonTransformationPipeline implements Transform<JsonObject> {
    private final List<Transform<JsonObject>> transformList = new ArrayList<>();

    @Override
    public JsonObject transform(JsonObject val) {
        for (Transform<JsonObject> transform : transformList) {
            val = transform.transform(val);
        }
        return val;
    }

    public JsonTransformationPipeline add(Transform<JsonObject> validator) {
        transformList.add(validator);
        return this;
    }

    public JsonTransformationPipeline remove(Transform<JsonObject> validator) {
        transformList.remove(validator);
        return this;
    }

    public JsonTransformationPipeline clear() {
        transformList.clear();
        return this;
    }
}
