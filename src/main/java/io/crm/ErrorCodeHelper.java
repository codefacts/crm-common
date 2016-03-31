package io.crm;

/**
 * Created by shahadat on 3/31/16.
 */
class ErrorCodeHelper {
    static private int validation = 3000_0_0001;
    static private final int validationHttp = 300;

    static private int error = 5000_0_0001;
    static private final int errorHttp = 500;

    static int validation() {
        return validation++;
    }

    static int validationHttp() {
        return validationHttp;
    }

    public static int error() {
        return error++;
    }

    public static int errorHttp() {
        return errorHttp;
    }
}
