package io.crm.promise.intfs;

/**
 * Created by someone on 17/11/2015.
 */
public interface FinishHandler<T> extends Invokable {
    public void accept(Stream<T> stream) throws Exception;
}
