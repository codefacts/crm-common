package io.crm.promise.intfs;

import io.crm.intfs.ConsumerInterface;

/**
 * Created by someone on 16/10/2015.
 */
public interface CompleteHandler<T> extends Invokable{
    public void accept(Promise<T> promise) throws Exception;
}
