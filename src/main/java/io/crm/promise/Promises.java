package io.crm.promise;

import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;
import io.vertx.core.Vertx;

import java.util.concurrent.TimeoutException;

/**
 * Created by someone on 16/10/2015.
 */
final public class Promises {

    private static final long DEFAULT_TIMEOUT = 3 * 10 * 1000;

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

    public static <T> Defer<T> defer(Vertx vertx) {
        return defer(vertx, -1);
    }

    public static <T> Defer<T> defer(Vertx vertx, final long timeoutMilis) {
        final long _timeoutMilis = timeoutMilis <= 0 ? DEFAULT_TIMEOUT : timeoutMilis;
        final PromiseImpl<T> promise = new PromiseImpl<>();
        vertx.setTimer(_timeoutMilis, l -> {
            if (!promise.isComplete())
                promise.fail(new TimeoutException("Promise timed out. Timeout: " + _timeoutMilis + " Delay: " + l));
        });
        return promise;
    }

    public static void main(String... args) {
        final Defer<Object> defer = defer(Vertx.vertx());
        defer.promise()
                .success(s -> System.out.println("1 : " + s))
                .error(System.out::println)
                .map(s -> s.toString())
                .map(s -> s.split("-"))
                .success(s -> System.out.println("2 : " + s))
                .map(s -> String.join("-", s))
                .complete(System.out::println)
                .error(System.out::println)
                .mapPromise(m -> Promises.success(m.split("5")))
                .complete(System.out::println)
                .map(s -> s.length)
                .success(s -> System.out.println("3 : " + s))
                .error(System.out::println)
                .map(null)
                .mapPromise(null)
                .complete(null)
                .then(null)
                .success(null)
                .error(null)
                .map(v -> {
                    return "[void]";
                })
                .success(s -> System.out.println("4 : " + s))
                .error(System.out::println)
        ;
//        defer.complete(new Exception("NO NO"));
    }

    public static void test6() {
        final Defer<Object> defer = defer(Vertx.vertx());
        defer.promise()
                .success(System.out::println)
                .error(System.out::println)
                .success(System.out::println)
                .complete(System.out::println)
                .error(System.out::println)
                .complete(System.out::println)
                .success(System.out::println)
                .error(System.out::println)
        ;
        defer.fail(new Exception("NO NO"));
    }

    public static void test5() {
        final Defer<Object> defer = defer(Vertx.vertx());
        defer.promise()
                .success(System.out::println)
                .error(System.out::println)
                .success(System.out::println)
                .complete(System.out::println)
                .error(System.out::println)
                .complete(System.out::println)
                .success(System.out::println)
                .error(System.out::println)
        ;
        defer.complete();
    }

    public static void test4() {
        final Defer<Object> defer = defer(Vertx.vertx());
        defer.promise()
                .success(System.out::println)
                .error(System.out::println)
                .success(System.out::println)
                .complete(System.out::println)
                .error(System.out::println)
                .complete(System.out::println)
                .success(System.out::println)
                .error(System.out::println)
        ;
        defer.complete("8888888888888888888");
    }

    public static void test3() {
        final Defer<Object> defer = defer(Vertx.vertx());
        defer.promise()
                .success(System.out::println)
                .error(System.out::println)
                .success(System.out::println)
                .complete(System.out::println)
                .error(System.out::println)
                .complete(System.out::println)
                .success(System.out::println)
                .error(System.out::println)
        ;
        defer.fail(new TimeoutException("Delay: 55"));
    }

    private static void test2() {
        error(new TimeoutException("Timed out: 55"))
                .success(System.out::println)
                .error(System.out::println)
                .success(System.out::println)
                .complete(System.out::println)
                .error(System.out::println)
                .complete(System.out::println)
                .success(System.out::println)
                .error(System.out::println)
        ;
    }

    private static void test1() {
        success("Sona")
                .success(System.out::println)
                .error(System.out::println)
                .success(System.out::println)
                .complete(System.out::println)
                .error(System.out::println)
                .complete(System.out::println)
                .success(System.out::println)
                .error(System.out::println)
        ;
    }
}
