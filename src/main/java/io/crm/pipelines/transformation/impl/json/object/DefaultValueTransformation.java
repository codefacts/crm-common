package io.crm.pipelines.transformation.impl.json.object;

import com.google.common.collect.ImmutableMap;
import io.crm.pipelines.transformation.Transform;
import io.crm.util.Util;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.Objects;

import static io.crm.util.Util.*;

/**
 * Created by shahadat on 3/1/16.
 */
public class DefaultValueTransformation implements Transform<JsonObject, JsonObject> {
    private final JsonObject defaultValue;

    public DefaultValueTransformation(JsonObject defaultValue) {

        this.defaultValue = toImmutable(defaultValue);

    }

    @Override
    public JsonObject transform(JsonObject val) {
        if (val == null) return new JsonObject();

        return recursive(val, defaultValue);
    }

    private JsonObject recursive(JsonObject json, JsonObject defJson) {

        defJson.getMap().forEach((key, defVal) -> {

            Object jsonValue = json.getValue(key);

            if (jsonValue == null) {
                json.put(key, emptyfy(defVal));
            } else if (jsonValue instanceof JsonObject) {
                recursive((JsonObject) jsonValue, as(defVal, JsonObject.class));
            }
        });
        return json;
    }

    private Object emptyfy(Object val) {

        Objects.requireNonNull(val);

        if (val instanceof JsonObject) {
            return new JsonObject();
        } else if (val instanceof JsonArray) {
            return new JsonArray();
        } else {
            return val;
        }
    }
}
