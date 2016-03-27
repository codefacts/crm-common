package io.crm.pipelines.transformation.impl.json.object;

import com.google.common.collect.ImmutableList;
import io.crm.pipelines.transformation.Transform;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by shahadat on 3/5/16.
 */
public class ObjectToArrayTransformation implements Transform<JsonObject, JsonArray> {
    private final List<String> keyOrder;

    public ObjectToArrayTransformation(List<String> keyOrder) {
        this.keyOrder = ImmutableList.copyOf(keyOrder);
    }

    @Override
    public JsonArray transform(JsonObject val) {
        JsonArray objects = new JsonArray();
        if (keyOrder != null) {
            keyOrder.forEach(key -> objects.add(new JsonObject().put(key, val.getValue(key))));
        } else {
            val.forEach(e -> objects.add(new JsonObject().put(e.getKey(), e.getValue())));
        }
        return objects;
    }
}
