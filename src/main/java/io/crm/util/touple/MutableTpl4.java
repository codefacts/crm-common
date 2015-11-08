package io.crm.util.touple;

/**
 * Created by someone on 23-Jun-2015.
 */
final public class MutableTpl4<T1, T2, T3, T4> {
    private T1 t1;
    private T2 t2;
    private T3 t3;
    private T4 t4;

    public MutableTpl4(final T1 t1, final T2 t2, final T3 t3, final T4 t4) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
    }

    public MutableTpl4() {

    }

    public T1 getT1() {
        return t1;
    }

    public void setT1(final T1 t1) {
        this.t1 = t1;
    }

    public T2 getT2() {
        return t2;
    }

    public void setT2(final T2 t2) {
        this.t2 = t2;
    }

    public T3 getT3() {
        return t3;
    }

    public void setT3(final T3 t3) {
        this.t3 = t3;
    }

    public T4 getT4() {
        return t4;
    }

    public void setT4(final T4 t4) {
        this.t4 = t4;
    }

    @Override
    public String toString() {
        return "Touple4{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                ", t4=" + t4 +
                '}';
    }
}
