package io.crm.promise;

/**
 * Created by someone on 16/10/2015.
 */
public class SuccessHandlerAlreadySetForPromise extends RuntimeException {
    public SuccessHandlerAlreadySetForPromise(String s) {
        super(s);
    }
}
