package io.crm.util;

import com.google.common.collect.Multimap;
import io.crm.QC;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.*;

/**
 * Created by someone on 18/08/2015.
 */
final public class ErrorBuilder {
    private final Map<String, List<JsonObject>> errorMap = new LinkedHashMap<>();

    public ErrorBuilder put(final String field, final JsonObject error) {
        List<JsonObject> list = errorMap.get(field);
        if (list == null) {
            list = new ArrayList<>();
            errorMap.put(field, list);
        }
        list.add(error);
        return this;
    }

    public ErrorBuilder put(final String field, final String message) {
        return put(field, new JsonObject().put(QC.message, message));
    }

    public ErrorBuilder putAll(final Collection<JsonObject> violations) {
        violations.forEach(v -> put(
                v.getString(QC.field), v));
        return this;
    }

    public JsonObject build() {
        return new JsonObject(cast(errorMap));
    }

    private Map<String, Object> cast(final Map map) {
        return map;
    }

    public static ErrorBuilder create() {
        return new ErrorBuilder();
    }
}
