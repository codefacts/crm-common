package io.crm.promise;

import io.crm.intfs.ConsumerUnchecked;
import io.crm.promise.intfs.*;

import java.util.HashMap;
import java.util.Map;

import static io.crm.util.Util.apply;

/**
 * Created by someone on 09/11/2015.
 */
final public class ConditionalPromiseImpl<T> implements ConditionalPromise<T> {
    private Promise<Decision<T>> promise;
    private final Map<String, ConsumerUnchecked<T>> map = new HashMap<>();
    private boolean done = false;

    ConditionalPromiseImpl(Promise<Decision<T>> promise) {
        this.promise = promise;
    }

    @Override
    public ConditionalPromise<T> on(final String condition, final ConsumerUnchecked<T> promiseConsumer) {
        map.put(condition, promiseConsumer);
        doStuff();
        return this;
    }

    @Override
    public ConditionalPromise<T> otherwise(final ConsumerUnchecked<T> promiseConsumer) {
        map.put(Decision.OTHERWISE, promiseConsumer);
        doStuff();
        return this;
    }

    private void doStuff() {
        promise = promise.then(decision -> {
            if (!done) {
                final ConsumerUnchecked<T> decisionHandler = apply(map.get(decision.decision),
                        pcu -> pcu == null ? map.get(Decision.OTHERWISE) : pcu);
                if (decisionHandler != null) {
                    done = true;
                    decisionHandler.accept(decision.retVal);
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

        Promises.from().decideAndMap(v -> Decision.of("ok", "JJ"))
        .on("ok", val -> val.notify());
        ;
        defer.complete("kkkk");
    }
}
