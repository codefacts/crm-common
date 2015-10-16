package io.crm.promise.intfs;

/**
 * Created by someone on 15/10/2015.
 */
public interface Defer<T> {

    public void fail(Throwable throwable);

    public void complete();

    public void complete(T value);

    public Promise<T> promise();
}
