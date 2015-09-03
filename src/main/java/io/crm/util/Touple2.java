package io.crm.util;

/**
 * Created by someone on 20/08/2015.
 */
final public class Touple2<T1, T2> {
    public T1 t1;
    public T2 t2;

    public Touple2() {
    }

    public Touple2(final T1 t1, final T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T1 getT1() {
        return t1;
    }

    public Touple2<T1, T2> t1(final T1 t1) {
        this.t1 = t1;
        return this;
    }

    public T2 getT2() {
        return t2;
    }

    public Touple2<T1, T2> t2(final T2 t2) {
        this.t2 = t2;
        return this;
    }
}
