package io.crm.pipelines.transformation.impl.json.object;

import io.crm.pipelines.transformation.Transform;
import io.vertx.core.json.JsonObject;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by shahadat on 3/5/16.
 */
public class IncludeExcludeTransformation implements Transform<JsonObject, JsonObject> {
    private final Set<String> includes;
    private final Set<String> excludes;

    public IncludeExcludeTransformation(Set<String> includes, Set<String> excludes) {
        this.includes = includes;
        this.excludes = excludes;
    }

    @Override
    public JsonObject transform(JsonObject json) {
        Stream<String> stream = json.getMap().keySet().stream();
        if (includes != null) {
            stream = stream.filter(k -> includes.contains(k));
        }
        if (excludes != null && excludes.size() > 0) {
            stream = stream.filter(k -> !excludes.contains(k));
        }
        JsonObject reduce = stream.reduce(new JsonObject(),
            (jsonObject, key) -> jsonObject.put(key, json.getValue(key)),
            (jsonObject1, jsonObject2) -> jsonObject1);
        return reduce;
    }
}