package io.crm.promise;

import diag.Watch;
import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;
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

    public static void main(String... args) {
        Watch watch = new Watch().start();
        for (int i = 0; i < 50_000_000; i++) {
            final Defer<Object> defer = defer();
            defer.promise()
//            Promises.success(55)
                    .mapTo(null)
                    .mapToPromise(null)
                    .mapToVoid(null)
                    .success(null)
                    .complete(null)
                    .error(null)
            ;
            defer.complete(31);
        }
        System.out.println(watch.end().elapsed() + " total: " + PromiseImpl.total);
    }

    public static void test8() {
        Watch watch = new Watch().start();
        for (int i = 0; i < 50_000_000; i++) {
            final Defer<Object> defer = defer();
            defer.promise()
//            Promises.success(55)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .mapTo(null)
                    .success(null)
                    .mapTo(null)
                    .complete(null)
                    .error(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapTo(null)
                    .complete(null)
                    .error(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapTo(null)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .success(null)
                    .error(null)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .success(null)
                    .error(null)
                    .mapToVoid(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .success(null)
                    .error(null)
            ;
            defer.complete(31);
        }
        System.out.println(watch.end().elapsed() + " total: " + PromiseImpl.total);
    }

    public static void test7() {
        Watch watch = new Watch().start();
        for (int i = 0; i < 10_000_000; i++) {
            final Defer<Object> defer = defer();
            defer.promise()
                    .success(null)
                    .error(null)
                    .mapTo(s -> s.toString())
                    .mapTo(s -> s.split("-"))
                    .success(null)
                    .mapTo(s -> String.join("-", s))
                    .complete(null)
                    .error(null)
                    .mapToPromise(m -> Promises.success(m.split("5")))
                    .complete(null)
                    .mapTo(s -> String.join("-", s))
                    .complete(null)
                    .error(null)
                    .mapToPromise(m -> Promises.success(m.split("5")))
                    .complete(null)
                    .mapTo(s -> s.length)
                    .success(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .success(null)
                    .error(null)
                    .mapTo(v -> {
                        return "[void]";
                    })
                    .success(null)
                    .error(null)
            ;
            defer.complete(123);
        }
        System.out.println(watch.end().elapsed() + " total: " + PromiseImpl.total);
    }

    public static void test6() {
        final Defer<Object> defer = defer();
        defer.promise()
                .success(null)
                .error(null)
                .success(null)
                .complete(null)
                .error(null)
                .complete(null)
                .success(null)
                .error(null)
        ;
        defer.fail(new Exception("NO NO"));
    }

    public static void test5() {
        final Defer<Object> defer = defer();
        defer.promise()
                .success(null)
                .error(null)
                .success(null)
                .complete(null)
                .error(null)
                .complete(null)
                .success(null)
                .error(null)
        ;
        defer.complete();
    }

    public static void test4() {
        final Defer<Object> defer = defer();
        defer.promise()
                .success(null)
                .error(null)
                .success(null)
                .complete(null)
                .error(null)
                .complete(null)
                .success(null)
                .error(null)
        ;
        defer.complete("8888888888888888888");
    }

    public static void test3() {
        final Defer<Object> defer = defer();
        defer.promise()
                .success(null)
                .error(null)
                .success(null)
                .complete(null)
                .error(null)
                .complete(null)
                .success(null)
                .error(null)
        ;
        defer.fail(new TimeoutException("Delay: 55"));
    }

    private static void test2() {
        error(new TimeoutException("Timed out: 55"))
                .success(null)
                .error(null)
                .success(null)
                .complete(null)
                .error(null)
                .complete(null)
                .success(null)
                .error(null)
        ;
    }

    private static void test1() {
        success("Sona")
                .success(null)
                .error(null)
                .success(null)
                .complete(null)
                .error(null)
                .complete(null)
                .success(null)
                .error(null)
        ;
    }
}
