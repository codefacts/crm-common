package io.crm.pipelines.transformation.impl.json.object;

import io.crm.pipelines.transformation.Transform;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by shahadat on 3/5/16.
 */
public class ConverterTransformation implements Transform<JsonObject, JsonObject> {
    final Map<String, Function<String, Object>> converters;

    public ConverterTransformation(Map<String, Function<String, Object>> converters) {
        this.converters = converters;
    }

    @Override
    public JsonObject transform(JsonObject json) {
        converters.forEach((k, v) -> {
            Object value = json.getValue(k);
            if (value != null) {
                json.put(k, v.apply(value.toString()));
            }
        });
        return json;
    }
}
