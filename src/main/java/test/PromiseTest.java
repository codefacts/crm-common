package test;

import diag.Watch;
import io.crm.promise.PromiseImpl;
import io.crm.promise.Promises;
import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;

import static io.crm.promise.Promises.*;

import java.util.concurrent.TimeoutException;

/**
 * Created by someone on 17/10/2015.
 */
public class PromiseTest {

    public static void main(String... args) {
        test8();
    }

    public static void test9() {
        Defer<Integer> first = Promises.defer();
        Promise<Integer> last = first.promise();
        for (int i = 0; i < 100000; i++) {
            last = last.mapTo(v -> v + 1);
        }
        Watch watch = new Watch().start();
        first.complete(0);
        last.then(v -> {
            System.out.println(v + " " + watch.end().elapsed());
        });
    }

    public static void test8() {
        Watch watch = new Watch().start();
        for (int i = 0; i < 10_000_000; i++) {
            final Defer<Object> defer = defer();
            defer.promise()
//            Promises.success(55)
                    .then(null)
                    .error(null)
                    .mapTo(null)
                    .mapTo(null)
                    .then(null)
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
                    .then(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .then(null)
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
                    .then(null)
                    .error(null)
                    .mapTo(null)
                    .then(null)
                    .error(null)
                    .then(null)
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
                    .then(null)
                    .error(null)
                    .mapTo(null)
                    .then(null)
                    .error(null)
                    .mapToVoid(null)
                    .error(null)
                    .mapTo(null)
                    .mapToPromise(null)
                    .complete(null)
                    .mapToVoid(null)
                    .then(null)
                    .error(null)
                    .mapTo(null)
                    .then(null)
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
                    .then(null)
                    .error(null)
                    .mapTo(s -> s.toString())
                    .mapTo(s -> s.split("-"))
                    .then(null)
                    .mapTo(s -> String.join("-", s))
                    .complete(null)
                    .error(null)
                    .mapToPromise(m -> Promises.from(m.split("5")))
                    .complete(null)
                    .mapTo(s -> String.join("-", s))
                    .complete(null)
                    .error(null)
                    .mapToPromise(m -> Promises.from(m.split("5")))
                    .complete(null)
                    .mapTo(s -> s.length)
                    .then(null)
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
                    .then(null)
                    .error(null)
                    .mapTo(v -> {
                        return "[void]";
                    })
                    .then(null)
                    .error(null)
            ;
            defer.complete(123);
        }
        System.out.println(watch.end().elapsed() + " total: " + PromiseImpl.total);
    }

    public static void test6() {
        final Defer<Object> defer = defer();
        defer.promise()
                .then(null)
                .error(null)
                .then(null)
                .complete(null)
                .error(null)
                .complete(null)
                .then(null)
                .error(null)
        ;
        defer.fail(new Exception("NO NO"));
    }

    public static void test5() {
        final Defer<Object> defer = defer();
        defer.promise()
                .then(null)
                .error(null)
                .then(null)
                .complete(null)
                .error(null)
                .complete(null)
                .then(null)
                .error(null)
        ;
        defer.complete();
    }

    public static void test4() {
        final Defer<Object> defer = defer();
        defer.promise()
                .then(null)
                .error(null)
                .then(null)
                .complete(null)
                .error(null)
                .complete(null)
                .then(null)
                .error(null)
        ;
        defer.complete("8888888888888888888");
    }

    public static void test3() {
        final Defer<Object> defer = defer();
        defer.promise()
                .then(null)
                .error(null)
                .then(null)
                .complete(null)
                .error(null)
                .complete(null)
                .then(null)
                .error(null)
        ;
        defer.fail(new TimeoutException("Delay: 55"));
    }

    private static void test2() {
        fromError(new TimeoutException("Timed out: 55"))
                .then(null)
                .error(null)
                .then(null)
                .complete(null)
                .error(null)
                .complete(null)
                .then(null)
                .error(null)
        ;
    }

    private static void test1() {
        from("Sona")
                .then(null)
                .error(null)
                .then(null)
                .complete(null)
                .error(null)
                .complete(null)
                .then(null)
                .error(null)
        ;
    }
}
