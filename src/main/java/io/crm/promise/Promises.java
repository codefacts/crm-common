package io.crm.promise;

import diag.Watch;
import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;
import io.crm.util.Touple2;
import io.vertx.core.Vertx;

import java.util.concurrent.TimeoutException;

/**
 * Created by someone on 16/10/2015.
 */
final public class Promises {

    public static Promise<Void> success() {
        return success(null);
    }

    public static <T> Promise<T> success(final T val) {
        final PromiseImpl<T> promise = new PromiseImpl<>();
        promise.complete(val);
        return promise;
    }

    public static Promise<Void> error(Throwable error) {
        final PromiseImpl<Void> promise = new PromiseImpl<>();
        promise.fail(error);
        return promise;
    }

    public static <T> Defer<T> defer() {
        final PromiseImpl<T> promise = new PromiseImpl<>();
        return promise;
    }

    public static <T1, T2> Promise<Touple2<T1, T2>> all(final Promise<T1> t1Promise, final Promise<T2> t2Promise) {
        final Defer<Touple2<T1, T2>> defer = defer();
        t1Promise
                .complete(t1p -> {
                    if (t1p.isSuccess()) {
                        t2Promise
                                .complete(t2p -> {
                                    if (t2p.isSuccess()) {
                                        defer.complete(new Touple2<T1, T2>(t1p.get(), t2p.get()));
                                    } else {
                                        defer.fail(t2p.error());
                                    }
                                });
                    } else {
                        defer.fail(t1p.error());
                    }
                })
        ;
        return defer.promise();
    }
}
