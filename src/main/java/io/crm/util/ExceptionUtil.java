package io.crm.util;

import io.crm.FailureCode;
import io.crm.intfs.Callable;
import io.crm.intfs.ConsumerInterface;
import io.crm.intfs.Runnable;
import io.vertx.core.AsyncResult;
import io.vertx.core.AsyncResultHandler;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

/**
 * Created by someone on 26-Jul-2015.
 */
final public class ExceptionUtil {

    public static void toRuntime(final Runnable runnable) {
        try {
            runnable.run();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toRuntimeCall(final Callable<T> runnable) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sallowRun(final Runnable runnable) {
        try {
            runnable.run();
        } catch (final Exception e) {
            logException(e);
        }
    }

    public static <T> T sallowCall(final Callable<T> runnable) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            logException(e);
        }
        return null;
    }

    public static <T> void then(final Callable<T> callable, final AsyncResultHandler<T> asyncResultHandler) {
        try {
            final T t = callable.call();
            asyncResultHandler.handle(AsyncUtil.success(t));
        } catch (final Exception ex) {
            asyncResultHandler.handle(AsyncUtil.fail(ex));
        }
    }

    public static <T> Handler<AsyncResult<T>> withReply(final ConsumerInterface<T> consumerInterface, final Message message) {
        return r -> {
            if (r.failed()) {
                ExceptionUtil.fail(message, r.cause());
                return;
            }
            withReplyRun(() -> consumerInterface.accept(r.result()), message);
        };
    }

    public static void withReplyRun(final Runnable runnable, final Message message) {
        try {
            runnable.run();
        } catch (final Exception e) {
            ExceptionUtil.fail(message, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T withReplyCall(final Callable<T> runnable, final Message message) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            ExceptionUtil.fail(message, e);
            throw new RuntimeException(e);
        }
    }

    public static void fail(final Message message, final Throwable throwable) {
        message.fail(FailureCode.InternalServerError.code, throwable.getMessage());
        System.out.println("FAILING MESSAGE: " + message + " <<>> CAUSE: " + throwable.getMessage());
    }

    public static void logException(final Throwable e) {
        System.err.println("Error: " + e.getClass() + " : " + e.getMessage());
    }
}
