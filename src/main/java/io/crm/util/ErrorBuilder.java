package io.crm.util;

import io.crm.QC;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by someone on 18/08/2015.
 */
final public class ErrorBuilder {
    private final Map<String, JsonArray> errorMap = new LinkedHashMap<>();

    public ErrorBuilder put(final String field, final JsonObject error) {
        JsonArray list = errorMap.get(field);
        if (list == null) {
            list = new JsonArray();
            errorMap.put(field, list);
        }
        list.add(error);
        return this;
    }

    public ErrorBuilder put(final String field, final String message) {
        return put(field, new JsonObject().put(QC.message, message));
    }

    public JsonObject get() {
        return new JsonObject(map(errorMap));
    }

    private Map<String, Object> map(final Map map) {
        return map;
    }

    public static ErrorBuilder create() {
        return new ErrorBuilder();
    }
}
