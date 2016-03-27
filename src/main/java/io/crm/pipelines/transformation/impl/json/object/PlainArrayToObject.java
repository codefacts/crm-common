package io.crm.pipelines.transformation.impl.json.object;

import com.google.common.collect.ImmutableList;
import io.crm.pipelines.transformation.Transform;
import io.crm.util.SimpleCounter;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by shahadat on 3/8/16.
 */
public class PlainArrayToObject implements Transform<JsonArray, JsonObject> {
    private final List<String> fields;

    public PlainArrayToObject(List<String> fields) {
        this.fields = ImmutableList.copyOf(fields);
    }

    @Override
    public JsonObject transform(JsonArray array) {
        JsonObject js = new JsonObject();
        SimpleCounter counter = new SimpleCounter(0);
        fields.forEach(field -> js.put(field, array.getValue(counter.counter++)));
        return js;
    }
}
