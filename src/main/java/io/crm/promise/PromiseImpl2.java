package io.crm.promise;

import io.crm.promise.intfs.*;

/**
 * Created by Shahadat on 8/23/2016.
 */
public class PromiseImpl2 implements Promise {
    @Override
    public Promise filter(FilterHandler predicateUnchecked) {
        return null;
    }

    @Override
    public ConditionalPromise decide(ThenDecideHandler valueConsumer) {
        return null;
    }

    @Override
    public Promise then(SuccessHandler successHandler) {
        return null;
    }

    @Override
    public Promise error(ErrorHandler errorHandler) {
        return null;
    }

    @Override
    public Promise complete(CompleteHandler completeHandler) {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public Object orElse(Object o) {
        return null;
    }

    @Override
    public Throwable error() {
        return null;
    }

    @Override
    public ConditionalPromise decideAndMapToPromise(MapToPromiseAndDecideHandler function) {
        return null;
    }

    @Override
    public ConditionalPromise decideAndMap(MapAndDecideHandler functionUnchecked) {
        return null;
    }

    @Override
    public Promise mapP(MapPHandler function) {
        return null;
    }

    @Override
    public Promise map(MapHandler functionUnchecked) {
        return null;
    }
}
