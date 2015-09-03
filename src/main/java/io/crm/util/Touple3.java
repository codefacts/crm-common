package io.crm.util;

final public class Touple3<T1, T2, T3> {
    private T1 t1;
    private T2 t2;
    private T3 t3;

    public Touple3(final T1 t1, final T2 t2, final T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public Touple3() {

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

    @Override
    public String toString() {
        return "Touple3{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                '}';
    }
}
