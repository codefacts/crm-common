package io.crm.promise;

/**
 * Created by someone on 09/11/2015.
 */
public class Decision<T> {
    public static final String OTHERWISE = "OTHERWISE";
    public final String decision;
    public final T retVal;

    private Decision(String decision, T retVal) {
        this.decision = decision;
        this.retVal = retVal;
    }

    public static <V> Decision<V> of(final String decision, final V retVal) {
        return new Decision(decision, retVal);
    }

    @Override
    public String toString() {
        return "Decision{" +
            "decision='" + decision + '\'' +
            ", retVal=" + retVal +
            '}';
    }
}
