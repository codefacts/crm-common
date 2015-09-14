package io.crm.util;

import io.crm.intfs.ConsumerInterface;
import io.crm.intfs.Runnable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static io.crm.util.ExceptionUtil.fail;

/**
 * Created by someone on 12-Aug-2015.
 */
final public class TaskCoordinator {
    private final Message message;
    private io.crm.intfs.Runnable onSuccess;
    private ConsumerInterface<Throwable> onError;
    private ConsumerInterface<TaskCoordinator> onComplete;
    private int count;
    private Throwable error;

    TaskCoordinator(final int count, final Message message, final Runnable onSuccess, final ConsumerInterface<Throwable> onError,
                    final ConsumerInterface<TaskCoordinator> onComplete) {
        this.count = count;
        this.message = message;
        this.onSuccess = onSuccess;
        this.onError = onError;
        this.onComplete = onComplete;

        onFinish(new ArrayList<>());
    }

    public <T> Handler<AsyncResult<T>> add(ConsumerInterface<T> consumer) {
        return r -> {
            final ArrayList<Exception> exceptions = new ArrayList<>();
            count--;
            if (r.failed()) {
                error = r.cause();
                fail(message, r.cause());
            } else {
                if (consumer != null) try {
                    consumer.accept(r.result());
                } catch (final Exception ex) {
                    fail(message, ex);
                    exceptions.add(ex);
                }
            }

            onFinish(exceptions);
        };
    }

    public <T> Handler<AsyncResult<T>> catchOnException(final ConsumerInterface<T> consumer) {
        return r -> {
            final ArrayList<Exception> exceptions = new ArrayList<>();
            if (r.failed()) {
                count--;
                error = r.cause();
                fail(message, r.cause());
            } else {
                if (consumer != null) try {
                    consumer.accept(r.result());
                } catch (final Exception ex) {
                    fail(message, ex);
                    exceptions.add(ex);
                }
            }

            onFinish(exceptions);
        };
    }

    private final void onFinish(final ArrayList<Exception> exceptions) {
        if (count <= 0) {
            if (error == null) {
                if (onSuccess != null) try {
                    onSuccess.run();
                } catch (final Exception ex) {
                    fail(message, ex);
                    exceptions.add(ex);
                }
            } else {
                if (onError != null) try {
                    onError.accept(error);
                } catch (final Exception ex) {
                    exceptions.add(ex);
                }
            }
            if (onComplete != null) try {
                onComplete.accept(this);
            } catch (final Exception ex) {
                ExceptionUtil.fail(message, ex);
                exceptions.add(ex);
            }
        }
        if (exceptions.size() > 0) {
            throw new RuntimeException("Exception in execution: " + String.join("; ", exceptions.stream().map(e -> e.getClass().getName() + " : " + e.getMessage()).collect(Collectors.toList())), exceptions.get(0));
        }
    }

    public void countdown(final int step) {
        count -= step;
        onFinish(new ArrayList<>());
    }

    public void countdown() {
        countdown(1);
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isSuccess() {
        return count <= 0 && error == null;
    }

    public boolean isComplete() {
        return count <= 0;
    }

    @Override
    public String toString() {
        return String.format("[%s complete: %s error: %s]", this.getClass().getSimpleName(), isComplete(), error);
    }

    public TaskCoordinator onSuccess(final Runnable onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    public TaskCoordinator onError(final ConsumerInterface<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    public TaskCoordinator onComplete(final ConsumerInterface<TaskCoordinator> onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    public static void main(String... args) {
        System.out.println(new TaskCoordinatorBuilder().get());
    }

    public void finish() {
        countdown(count);
    }

    public void signalError(final Throwable throwable) {
        error = throwable;
        fail(message, error);
        countdown();
    }
}
