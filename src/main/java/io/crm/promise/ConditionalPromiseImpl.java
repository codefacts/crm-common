package io.crm.promise;

import io.crm.intfs.ConsumerUnchecked;
import io.crm.promise.intfs.CompleteHandler;
import io.crm.promise.intfs.ErrorHandler;
import io.crm.promise.intfs.Promise;
import io.crm.promise.intfs.ConditionalPromise;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by someone on 09/11/2015.
 */
final public class ConditionalPromiseImpl<T> implements ConditionalPromise<T> {
    private static final String OTHERWISE = "OTHERWISE";
    private final Promise<Decision<T>> promise;
    private final Map<String, ConsumerUnchecked<Promise<T>>> map = new HashMap<>();

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
        promise.then(decision -> {
            final ConsumerUnchecked<Promise<T>> decisionHandler = map.get(decision.decision);
            if (decisionHandler == null)
                throw new NoDecisionHandlerIsGiven("No DecisionHandler was given for decision: " + decision.decision + ". Decision: " + decision);
            decisionHandler.accept(Promises.from(decision.retVal));
        });
    }

    @Override
    public ConditionalPromise<T> error(final ErrorHandler errorHandler) {
        promise.error(errorHandler);
        return this;
    }

    @Override
    public ConditionalPromise<T> complete(final CompleteHandler<T> completeHandler) {
        promise.complete(p -> {
            Promises.from(p.get().retVal).complete(completeHandler);
        });
        return this;
    }
}
