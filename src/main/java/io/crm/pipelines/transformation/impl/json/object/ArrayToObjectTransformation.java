package io.crm.pipelines.transformation.impl.json.object;

import io.crm.pipelines.transformation.Transform;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Set;

/**
 * Created by shahadat on 3/5/16.
 */
public class ArrayToObjectTransformation implements Transform<JsonArray, JsonObject> {

    @Override
    public JsonObject transform(JsonArray array) {

        if (array == null) return null;

        JsonObject entries = new JsonObject();
        array.forEach(obj -> {
            JsonObject json = (JsonObject) obj;
            entries.getMap().putAll(json.getMap());
        });
        return entries;
    }
}
