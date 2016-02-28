package io.crm;

import static io.crm.Helper.validation;
import static io.crm.Helper.validationHttp;

/**
 * Created by shahadat on 2/28/16.
 */
public enum FailureCodes {

    VALIDATION_ERROR(validation(), "validation.error", validationHttp()),
    INVALID_TYPE_VALIDATION_ERROR(validation(), "invalid.type.validation.error", validationHttp()),
    INVALID_RANGE_VALIDATION_ERROR(validation(), "invalid.range.validation.error", validationHttp()),
    NULL_VALIDATION_ERROR(validation(), "null.validation.error", validationHttp()),
    INVALID_VALUE_VALIDATION_ERROR(validation(), "invalid.value.validation.error", validationHttp()), PATTERN_VALIDATION_ERROR(validation(), "pattern.validation.error", validationHttp()),
    MAX_LENGTH_VALIDATION_ERROR(validation(), "max.length.validation.error", validationHttp()), MIN_LENGTH_VALIDATION_ERROR(validation(), "min.length.validation.error", validationHttp()), LENGTH_VALIDATION_ERROR(validation(), "length.validation.error", validationHttp()), INVALID_EMAIL_VALIDATION_ERROR(validation(), "invalid.email.validation.error", validationHttp()), INVALID_PHONE_VALIDATION_ERROR(validation(), "invalid.phone.validation.error", validationHttp());

    private final int code;
    private final String messageCode;
    private final int httpResponseCode;

    FailureCodes(int code, String messageCode, int httpResponseCode) {
        this.code = code;
        this.messageCode = messageCode;
        this.httpResponseCode = httpResponseCode;
    }

    public int code() {
        return code;
    }

    public String messageCode() {
        return messageCode;
    }

    public int httpResponseCode() {
        return httpResponseCode;
    }
}

class Helper {
    static private int validation = 30001;
    static private int validationHttp = 300;

    static int validation() {
        return validation++;
    }

    static int validationHttp() {
        return validationHttp++;
    }
}
