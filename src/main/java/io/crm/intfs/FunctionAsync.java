package io.crm.intfs;

/**
 * Created by someone on 16/10/2015.
 */
public interface FunctionAsync<T, R> {
    public R apply(T value) throws Exception;
}
