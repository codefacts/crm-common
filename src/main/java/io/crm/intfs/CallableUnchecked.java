package io.crm.intfs;

/**
 * Created by someone on 26-Jul-2015.
 */
public interface CallableUnchecked<T> {
    public T call() throws Exception;
}
