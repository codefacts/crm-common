package test;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Defer;

import java.util.Arrays;

/**
 * Created by sohan on 10/17/2015.
 */
public class PromiseAllTest {
    public static void main(String... args) {
        Defer<Object> defer = Promises.defer();
        Defer<Object> defer1 = Promises.defer();
        Defer<Object> defer2 = Promises.defer();
        Promises.all(
                defer.promise(), defer1.promise(), defer2.promise()
        ).complete(System.out::println).success(System.out::println).error(System.out::println);
        defer1.complete(new Exception("LOLO"));
        defer.complete(new Exception("GG"));
        defer2.complete("ok");
    }
}
