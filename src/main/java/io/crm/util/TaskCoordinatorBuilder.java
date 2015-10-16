package io.crm.util;

import io.crm.intfs.ConsumerUnchecked;
import io.crm.intfs.RunnableUnchecked;
import io.vertx.core.eventbus.Message;

/**
 * Created by someone on 12-Aug-2015.
 */
final public class TaskCoordinatorBuilder {
    private Message message;
    private RunnableUnchecked onSuccess;
    private ConsumerUnchecked<Throwable> onError;
    private ConsumerUnchecked<TaskCoordinator> onComplete;
    private int count;

    public int count() {
        return count;
    }

    public Message message() {
        return message;
    }

    public TaskCoordinatorBuilder count(final int count) {
        this.count = count;
        return this;
    }

    public TaskCoordinatorBuilder message(final Message message) {
        this.message = message;
        return this;
    }

    public TaskCoordinatorBuilder onSuccess(final RunnableUnchecked onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    public TaskCoordinatorBuilder onError(final ConsumerUnchecked<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    public TaskCoordinatorBuilder onComplete(final ConsumerUnchecked<TaskCoordinator> onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    public TaskCoordinator get() {
        return new TaskCoordinator(count, message, onSuccess, onError, onComplete);
    }

    public static TaskCoordinatorBuilder create() {
        return new TaskCoordinatorBuilder();
    }
}
