package io.crm.promise;

/**
 * Created by someone on 20/10/2015.
 */
public class PromiseCallbackAlreadySet extends RuntimeException {
    public PromiseCallbackAlreadySet(final String message) {
        super(message);
    }
}
