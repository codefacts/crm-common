package io.crm.util.touple;

import java.util.function.BiFunction;

/**
 * Created by someone on 20/08/2015.
 */
final public class MutableTpl2<T1, T2> {
    public T1 t1;
    public T2 t2;

    public MutableTpl2() {
    }

    public MutableTpl2(final T1 t1, final T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T1 getT1() {
        return t1;
    }

    public MutableTpl2<T1, T2> t1(final T1 t1) {
        this.t1 = t1;
        return this;
    }

    public T2 getT2() {
        return t2;
    }

    public MutableTpl2<T1, T2> t2(final T2 t2) {
        this.t2 = t2;
        return this;
    }

    public <R> R apply(final BiFunction<T1, T2, R> biFunction) {
        return biFunction.apply(t1, t2);
    }
}
