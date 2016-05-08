package statemachine;

/**
 * Created by Khan on 5/7/2016.
 */
public class StateTrigger<T> {
    private final String event;
    private final T message;

    private StateTrigger(String event, T message) {
        this.event = event;
        this.message = message;
    }

    static <T> StateTrigger<T> create(String event, T message) {
        return new StateTrigger<T>(event, message);
    }
}
