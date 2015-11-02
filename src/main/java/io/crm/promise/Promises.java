package io.crm.promise;

import com.google.common.collect.ImmutableList;
import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;
import io.crm.util.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by someone on 16/10/2015.
 */
final public class Promises {

    public static Promise<Void> success() {
        return success(null);
    }

    public static <T> Promise<T> success(final T val) {
        final PromiseImpl<T> promise = new PromiseImpl<>(null, null);
        promise.complete(val);
        return promise;
    }

    public static Promise<Void> error(Throwable error) {
        final PromiseImpl<Void> promise = new PromiseImpl<>(null, null);
        promise.fail(error);
        return promise;
    }

    public static <T> Defer<T> defer() {
        final PromiseImpl<T> promise = new PromiseImpl<>(null, null);
        return promise;
    }

    public static <T> Promise<List<T>> all(final Collection<Promise<T>> promises) {
        if (promises.size() == 0) {
            return Promises.success(ImmutableList.of());
        }
        Defer<List<T>> defer = defer();
        final SimpleCounter counter = new SimpleCounter(0);
        Touple2<Boolean, Throwable> pStatus = new Touple2<>();
        promises.forEach(pm -> {
            pm.complete(pms -> {
                pStatus.t1 &= pms.isSuccess();
                pStatus.t2 = pStatus.t2 == null ? pms.error() : pStatus.t2;
                counter.counter++;
                if (counter.counter == promises.size()) {
                    if (pStatus.t1) {
                        ImmutableList.Builder<T> builder = ImmutableList.builder();
                        promises.forEach(promise -> builder.add(promise.get()));
                        defer.complete(builder.build());
                    } else {
                        defer.fail(pStatus.t2);
                    }
                }
            });
        });
        return defer.promise();
    }

    public static <T1, T2> Promise<Touple2<T1, T2>> all(final Promise<T1> t1Promise, final Promise<T2> t2Promise) {
        final Defer<Touple2<T1, T2>> defer = defer();
        SimpleCounter counter = new SimpleCounter();
        Touple2<T1, T2> touple2 = new Touple2<>();
        final int len = 2;
        t1Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple2.t1 = t.get();
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple2);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t2Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple2.t2 = t.get();
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple2);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });
        return defer.promise();
    }

    public static <T1, T2, T3> Promise<Touple3<T1, T2, T3>> all(final Promise<T1> t1Promise,
                                                                final Promise<T2> t2Promise,
                                                                final Promise<T3> t3Promise) {
        final Defer<Touple3<T1, T2, T3>> defer = defer();
        SimpleCounter counter = new SimpleCounter();
        Touple3<T1, T2, T3> touple3 = new Touple3<>();
        final int len = 3;
        t1Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple3.setT1(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple3);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t2Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple3.setT2(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple3);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t3Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple3.setT3(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple3);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });
        return defer.promise();
    }


    public static <T1, T2, T3, T4> Promise<Touple4<T1, T2, T3, T4>> all(final Promise<T1> t1Promise,
                                                                        final Promise<T2> t2Promise,
                                                                        final Promise<T3> t3Promise,
                                                                        final Promise<T4> t4Promise) {
        final Defer<Touple4<T1, T2, T3, T4>> defer = defer();
        SimpleCounter counter = new SimpleCounter();
        Touple4<T1, T2, T3, T4> touple4 = new Touple4<>();
        final int len = 4;
        t1Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple4.setT1(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t2Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple4.setT2(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t3Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple4.setT3(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t4Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    touple4.setT4(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(touple4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });
        return defer.promise();
    }
}
