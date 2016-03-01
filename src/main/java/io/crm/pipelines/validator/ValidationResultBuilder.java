package io.crm.pipelines.validator;

import io.vertx.core.json.JsonObject;

import static io.crm.util.Util.or;

public class ValidationResultBuilder {
    private String fieldName;
    private Object value;
    private int errorCode;
    private String messageCode;
    private String message;
    private JsonObject additionals;

    public ValidationResultBuilder setField(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ValidationResultBuilder setValue(Object value) {
        this.value = value;
        return this;
    }

    public ValidationResultBuilder setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ValidationResultBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationResultBuilder setMessageCode(String messageCode) {
        this.messageCode = messageCode;
        return this;
    }

    public ValidationResultBuilder setAdditionals(JsonObject additionals) {
        this.additionals = additionals;
        return this;
    }

    public ValidationResult createValidationResult() {
        return new ValidationResult(fieldName, value, errorCode, messageCode, message, additionals);
    }
}