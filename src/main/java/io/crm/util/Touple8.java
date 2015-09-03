package io.crm.util;

/**
 * Created by someone on 23-Jun-2015.
 */
final public class Touple8<T1, T2, T3, T4, T5, T6, T7, T8> {
    private T1 t1;
    private T2 t2;
    private T3 t3;
    private T4 t4;
    private T5 t5;
    private T6 t6;
    private T7 t7;
    private T8 t8;

    public Touple8(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
    }

    public Touple8() {

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

    public T5 getT5() {
        return t5;
    }

    public void setT5(final T5 t5) {
        this.t5 = t5;
    }

    public T6 getT6() {
        return t6;
    }

    public void setT6(final T6 t6) {
        this.t6 = t6;
    }

    public T7 getT7() {
        return t7;
    }

    public void setT7(final T7 t7) {
        this.t7 = t7;
    }

    public T8 getT8() {
        return t8;
    }

    public void setT8(final T8 t8) {
        this.t8 = t8;
    }

    @Override
    public String toString() {
        return "Touple8{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                ", t4=" + t4 +
                ", t5=" + t5 +
                ", t6=" + t6 +
                ", t7=" + t7 +
                ", t8=" + t8 +
                '}';
    }
}
