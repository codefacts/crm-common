package io.crm.pipelines.transformation.impl.json.object;

import io.crm.pipelines.transformation.Transform;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by shahadat on 3/5/16.
 */
public class RemoveNullsTransformation implements Transform<JsonObject, JsonObject> {
    @Override
    public JsonObject transform(JsonObject json) {
        json.getMap().entrySet().removeIf(e -> e.getValue() == null);
        return json;
    }
}
