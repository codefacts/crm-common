package io.crm.pipelines.validator;

import io.vertx.core.json.JsonObject;

import static io.crm.util.Util.or;

/**
 * Created by shahadat on 2/28/16.
 */
public class ValidationResult<T> {
    private final String field;
    private final T value;
    private final int errorCode;
    private final String messageCode;
    private final String message;
    private final JsonObject additionals;

    public ValidationResult(String fieldName, T value, int errorCode, String messageCode, String message, JsonObject additionals) {
        this.field = fieldName;
        this.value = value;
        this.errorCode = errorCode;
        this.messageCode = messageCode;
        this.message = message;
        this.additionals = additionals;
    }

    public String getField() {
        return or(field, "");
    }

    public T getValue() {
        return value;
    }

    public T getValue(T defaultVal) {
        return or(value, defaultVal);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageCode() {
        return or(messageCode, "");
    }

    public String getMessage() {
        return or(message, "");
    }

    public JsonObject getAdditionals() {
        return or(additionals, new JsonObject());
    }

    public JsonObject toJson() {
        return additionals
            .put("field", field)
            .put("value", value)
            .put("errorCode", errorCode)
            .put("message", message);
    }
}