package io.crm.intfs;

/**
 * Created by someone on 08/11/2015.
 */
public interface TriConsumerUnchecked<T1, T2, T3> {
    public void accept(T1 t1, T2 t2, T3 t3) throws Exception;
}
