package io.crm.promise;

import com.google.common.collect.ImmutableList;
import io.crm.intfs.CallableUnchecked;
import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;
import io.crm.util.*;
import io.crm.util.touple.MutableTpl2;
import io.crm.util.touple.MutableTpl3;
import io.crm.util.touple.MutableTpl4;
import io.crm.util.touple.MutableTpls;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by someone on 16/10/2015.
 */
final public class Promises {

    public static Promise<Void> from() {
        return from(null);
    }

    public static <T> Promise<T> from(final T val) {
        final PromiseImpl<T> promise = new PromiseImpl<>(null, null);
        promise.complete(val);
        return promise;
    }

    public static <T> Promise<T> from(final CallableUnchecked<T> callableUnchecked) {
        Defer<T> defer = defer();
        try {
            T retVal = callableUnchecked.call();
            defer.complete(retVal);
        } catch (Exception ex) {
            defer.fail(ex);
        }
        return defer.promise();
    }

    public static <T> Promise<T> fromError(Throwable error) {
        final PromiseImpl<T> promise = new PromiseImpl<>(null, null);
        promise.fail(error);
        return promise;
    }

    public static <T> Defer<T> defer() {
        final PromiseImpl<T> promise = new PromiseImpl<>(null, null);
        return promise;
    }

    public static <T> Promise<List<T>> when(final Collection<Promise<T>> promises) {
        if (promises.size() == 0) {
            return Promises.from(ImmutableList.of());
        }
        Defer<List<T>> defer = defer();
        final SimpleCounter counter = new SimpleCounter(0);
        MutableTpl2<Boolean, Throwable> pStatus = MutableTpls.of(true, null);
        promises.forEach(pm -> pm.complete(pms -> {
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
        }));
        return defer.promise();
    }

    public static <T> Promise<List<Promise<T>>> allComplete(final Collection<Promise<T>> promises) {
        final Defer<List<Promise<T>>> defer = Promises.defer();
        if (promises.size() <= 0) defer.complete(Collections.emptyList());
        try {
            final ImmutableList.Builder<Promise<T>> builder = ImmutableList.builder();
            final SimpleCounter counter = new SimpleCounter(0);
            promises.forEach(promise -> {
                final Promise<T> complete = promise.complete(p -> {
                    counter.counter++;
                    if (counter.counter == promises.size()) {
                        defer.complete(builder.build());
                    }
                });
                builder.add(complete);
            });
        } catch (Exception ex) {
            defer.fail(ex);
        }
        return defer.promise();
    }

    public static <T1, T2> Promise<MutableTpl2<T1, T2>> when(final Promise<T1> t1Promise, final Promise<T2> t2Promise) {
        final Defer<MutableTpl2<T1, T2>> defer = defer();
        SimpleCounter counter = new SimpleCounter();
        MutableTpl2<T1, T2> mutableTpl2 = new MutableTpl2<>();
        final int len = 2;
        t1Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl2.t1 = t.get();
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl2);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t2Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl2.t2 = t.get();
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl2);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });
        return defer.promise();
    }

    public static <T1, T2, T3> Promise<MutableTpl3<T1, T2, T3>> when(final Promise<T1> t1Promise,
                                                                     final Promise<T2> t2Promise,
                                                                     final Promise<T3> t3Promise) {
        final Defer<MutableTpl3<T1, T2, T3>> defer = defer();
        SimpleCounter counter = new SimpleCounter();
        MutableTpl3<T1, T2, T3> mutableTpl3 = new MutableTpl3<>();
        final int len = 3;
        t1Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl3.setT1(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl3);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t2Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl3.setT2(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl3);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t3Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl3.setT3(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl3);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });
        return defer.promise();
    }


    public static <T1, T2, T3, T4> Promise<MutableTpl4<T1, T2, T3, T4>> when(final Promise<T1> t1Promise,
                                                                             final Promise<T2> t2Promise,
                                                                             final Promise<T3> t3Promise,
                                                                             final Promise<T4> t4Promise) {
        final Defer<MutableTpl4<T1, T2, T3, T4>> defer = defer();
        SimpleCounter counter = new SimpleCounter();
        MutableTpl4<T1, T2, T3, T4> mutableTpl4 = new MutableTpl4<>();
        final int len = 4;
        t1Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl4.setT1(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t2Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl4.setT2(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t3Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl4.setT3(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });

        t4Promise.complete(t -> {
            if (!defer.promise().isComplete()) {
                if (t.isSuccess()) {
                    mutableTpl4.setT4(t.get());
                    counter.counter++;
                    if (counter.counter == len) {
                        defer.complete(mutableTpl4);
                    }
                } else {
                    defer.fail(t.error());
                }
            }
        });
        return defer.promise();
    }
}
