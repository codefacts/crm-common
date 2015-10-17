package test;

import diag.Watch;
import io.crm.promise.PromiseImpl;
import io.crm.promise.Promises;
import io.crm.promise.intfs.Defer;

import static io.crm.promise.Promises.*;

import java.util.concurrent.TimeoutException;

/**
 * Created by someone on 17/10/2015.
 */
public class PromiseTest {

    public static void main(String... args) {
        test8();
    }

    public static void test8() {
        Watch watch = new Watch().start();
        for (int i = 0; i < 10_000_000; i++) {
//            final Defer<Object> defer = defer();
//            defer.promise()
            Promises.success(55)
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
//            defer.complete(31);
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
