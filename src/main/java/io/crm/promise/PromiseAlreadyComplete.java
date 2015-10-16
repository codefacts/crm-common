package io.crm.promise;

/**
 * Created by someone on 16/10/2015.
 */
public class PromiseAlreadyComplete extends RuntimeException {
    public PromiseAlreadyComplete(String s) {
        super(s);
    }
}
