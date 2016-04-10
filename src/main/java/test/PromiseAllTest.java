package test;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Defer;

/**
 * Created by sohan on 10/17/2015.
 */
public class PromiseAllTest {
//    public static void main(String... args) {
////        Defer<Object> defer = Promises.defer();
////        Defer<Object> defer1 = Promises.defer();
////        Defer<Object> defer2 = Promises.defer();
////        Promises.when(
////                defer.promise(), defer1.promise(), defer2.promise()
////        ).complete(System.out::println).then(System.out::println).error(System.out::println);
////        defer1.complete(new Exception("LOLO"));
////        defer.complete(new Exception("GG"));
////        defer2.complete("ok");
//
//
//    }

    public static void main(String... args) {
        Defer<Object> defer = Promises.defer();
        defer.promise()
            .filter(v ->
                v.equals("OK"))
            .then(v -> System.out.println("v: " + v))
            .complete(System.out::println)
            .error(Throwable::printStackTrace)
            .then(v -> System.out.println("v: " + v))
            .complete(System.out::println)
            .error(e -> System.out.println("OHHHH"))
            .decide(v -> "do")
            .on("do", v -> {
                System.out.println("do");
            })
            .contnue(v -> System.out.println("other"))
            .complete(p -> System.out.println(p))
            .error(e -> System.out.println(e))
            .error(e -> e.printStackTrace());
        defer.fail(new Exception("KONA"));
    }
}
