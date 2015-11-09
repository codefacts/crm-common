package io.crm.promise;

import io.crm.intfs.ConsumerUnchecked;
import io.crm.promise.intfs.*;
import io.crm.util.Util;

import java.util.HashMap;
import java.util.Map;

import static io.crm.util.Util.apply;

/**
 * Created by someone on 09/11/2015.
 */
final public class ConditionalPromiseImpl<T> implements ConditionalPromise<T> {
    private static final String OTHERWISE = "OTHERWISE";
    private Promise<Decision<T>> promise;
    private final Map<String, ConsumerUnchecked<Promise<T>>> map = new HashMap<>();
    private boolean done = false;

    ConditionalPromiseImpl(Promise<Decision<T>> promise) {
        this.promise = promise;
    }

    @Override
    public ConditionalPromise<T> on(final String condition, final ConsumerUnchecked<Promise<T>> promiseConsumer) {
        map.put(condition, promiseConsumer);
        doStuff();
        return this;
    }

    @Override
    public ConditionalPromise<T> otherwise(final ConsumerUnchecked<Promise<T>> promiseConsumer) {
        map.put(OTHERWISE, promiseConsumer);
        doStuff();
        return this;
    }

    private void doStuff() {
        promise = promise.then(decision -> {
            if (!done) {
                final ConsumerUnchecked<Promise<T>> decisionHandler = apply(map.get(decision.decision),
                        pcu -> pcu == null ? map.get(OTHERWISE) : pcu);
                if (decisionHandler != null) {
                    done = true;
                    decisionHandler.accept(Promises.from(decision.retVal));
                }
            }
        });
    }

    @Override
    public ConditionalPromise<T> error(final ErrorHandler errorHandler) {
        promise = promise.error(errorHandler);
        return this;
    }

    @Override
    public ConditionalPromise<T> complete(final CompleteHandler<T> completeHandler) {
        promise = promise.complete(p -> {
            Promises.from(p.isSuccess() ? p.get().retVal : null).complete(completeHandler);
        });
        return this;
    }

    public static void main(String... args) {
        final Defer<Object> defer = Promises.defer();
        defer.promise()
//        Promises.from()
                .mapAndDecide(aVoid -> Decision.dec("no", "sona"))
                .on("no", p -> {
                    System.out.println("P >>> " + p + " >>> no");
                })
                .on("go", p -> {
                    System.out.println("P >>> " + p + " >>> go");
                })
                .otherwise(p -> {
                    System.out.println("otherwise");
                })
                .error(Throwable::printStackTrace)
                .complete(System.out::println)
        ;
        defer.complete("kkkk");
    }
}
