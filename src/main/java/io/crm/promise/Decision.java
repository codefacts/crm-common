package io.crm.promise;

/**
 * Created by someone on 09/11/2015.
 */
public class Decision<T> {
    public final String decision;
    public final T retVal;

    public Decision(String decision, T retVal) {
        this.decision = decision;
        this.retVal = retVal;
    }

    public <V> Decision dec() {
        return null;
    }
}
