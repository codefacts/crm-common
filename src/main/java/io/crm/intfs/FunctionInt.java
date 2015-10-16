package io.crm.intfs;

/**
 * Created by someone on 15/10/2015.
 */
public interface FunctionInt<T, R> {
    public R apply(T t) throws Exception;
}
