package io.crm.util;

import io.crm.intfs.ConsumerInterface;
import io.crm.intfs.Runnable;
import io.vertx.core.eventbus.Message;

/**
 * Created by someone on 12-Aug-2015.
 */
final public class TaskCoordinatorBuilder {
    private Message message;
    private io.crm.intfs.Runnable onSuccess;
    private ConsumerInterface<Throwable> onError;
    private ConsumerInterface<TaskCoordinator> onComplete;
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

    public TaskCoordinatorBuilder onSuccess(final Runnable onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    public TaskCoordinatorBuilder onError(final ConsumerInterface<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    public TaskCoordinatorBuilder onComplete(final ConsumerInterface<TaskCoordinator> onComplete) {
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
