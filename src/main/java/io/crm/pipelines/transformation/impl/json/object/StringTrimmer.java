package io.crm.pipelines.transformation.impl.json.object;

import io.crm.pipelines.transformation.Transform;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by shahadat on 3/5/16.
 */
public class StringTrimmer implements Transform<JsonObject, JsonObject> {
    private Set<String> includes;
    private Set<String> excludes;

    @Override
    public JsonObject transform(JsonObject json) {
        if (json == null) return null;

        Stream<String> stream = json.getMap().keySet().stream();
        if (includes != null) {
            stream = stream.filter(s -> includes.contains(s));
        }
        if (excludes != null && excludes.size() > 0) {
            stream = stream.filter(s -> !excludes.contains(s));
        }
        stream
            .filter(key -> json.getValue(key) instanceof String)
            .forEach(key -> {
                json.put(key, json.getString(key).trim());
            });
        return json;
    }

    public StringTrimmer setIncludes(Set<String> includes) {
        this.includes = includes;
        return this;
    }

    public StringTrimmer setExcludes(Set<String> excludes) {
        this.excludes = excludes;
        return this;
    }

    public static void main(String... args) {
        JsonObject transform = new StringTrimmer()
            .setIncludes(new HashSet<>(Arrays.asList("a", "b", "c", "d")))
            .setExcludes(new HashSet<>(Arrays.asList("b", "d")))
            .transform(new JsonObject()
//                .put("a", "  aaA  ")
//                .put("b", "   b   ")
//                .put("c", "  c  ")
//                .put("d", "  d  ")
            );
        System.out.println(transform.encodePrettily());
    }
}
