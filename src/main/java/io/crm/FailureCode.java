package io.crm;

/**
 * Created by someone on 18/08/2015.
 */
public enum FailureCode {
    validationError(1, "Validation Error"),
    BadRequest(2, "Bad Request"),
    InternalServerError(3, "Internal Server Error"),
    UnknownError(4, "Unknown response from service");

    public final int code;
    public final String message;

    FailureCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
