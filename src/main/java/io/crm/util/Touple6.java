package io.crm.util;

/**
 * Created by someone on 23-Jun-2015.
 */
final public class Touple6<T1, T2, T3, T4, T5, T6> {
    private T1 t1;
    private T2 t2;
    private T3 t3;
    private T4 t4;
    private T5 t5;
    private T6 t6;

    public Touple6(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
    }

    public Touple6() {

    }

    public T1 getT1() {
        return t1;
    }

    public void setT1(T1 t1) {
        this.t1 = t1;
    }

    public T2 getT2() {
        return t2;
    }

    public void setT2(T2 t2) {
        this.t2 = t2;
    }

    public T3 getT3() {
        return t3;
    }

    public void setT3(T3 t3) {
        this.t3 = t3;
    }

    public T4 getT4() {
        return t4;
    }

    public void setT4(T4 t4) {
        this.t4 = t4;
    }

    public T5 getT5() {
        return t5;
    }

    public void setT5(T5 t5) {
        this.t5 = t5;
    }

    public T6 getT6() {
        return t6;
    }

    public void setT6(T6 t6) {
        this.t6 = t6;
    }

    @Override
    public String toString() {
        return "Touple6{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                ", t4=" + t4 +
                ", t5=" + t5 +
                ", t6=" + t6 +
                '}';
    }
}
