package io.crm.intfs;

/**
 * Created by someone on 20/09/2015.
 */
public interface BiConsumerUnchecked<T1, T2> {
    public void accept(T1 t1, T2 t2) throws Exception;
}
