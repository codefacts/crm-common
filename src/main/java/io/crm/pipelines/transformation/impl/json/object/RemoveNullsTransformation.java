package io.crm.pipelines.transformation.impl.json.object;

import io.crm.pipelines.transformation.Transform;
import io.crm.util.Util;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by shahadat on 3/5/16.
 */
public class RemoveNullsTransformation implements Transform<JsonObject, JsonObject> {
    private final RecursiveMerge recursiveMerge;

    public RemoveNullsTransformation() {
        this(null, null);
    }

    public RemoveNullsTransformation(Set<List<String>> includes, Set<List<String>> excludes) {
        recursiveMerge = new RecursiveMerge(includes, excludes,
            o -> o == null, (e, remove) -> remove.run(),
            o -> o == null, (v, remove) -> remove.run());
    }

    @Override
    public JsonObject transform(JsonObject json) {
        JsonObject transform = recursiveMerge.transform(json);
        return transform;
    }
}
