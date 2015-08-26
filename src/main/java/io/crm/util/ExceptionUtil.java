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
public class ExceptionUtil {

    public static void toRuntime(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toRuntimeCall(Callable<T> runnable) {
        try {
            return runnable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sallowRun(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            logException(e);
        }
    }

    public static <T> T sallowCall(Callable<T> runnable) {
        try {
            return runnable.call();
        } catch (Exception e) {
            logException(e);
        }
        return null;
    }

    public static <T> void then(Callable<T> callable, AsyncResultHandler<T> asyncResultHandler) {
        try {
            T t = callable.call();
            asyncResultHandler.handle(AsyncUtil.success(t));
        } catch (Exception ex) {
            asyncResultHandler.handle(AsyncUtil.fail(ex));
        }
    }

    public static <T> Handler<AsyncResult<T>> withReply(ConsumerInterface<T> consumerInterface, Message message) {
        return r -> {
            if (r.failed()) {
                ExceptionUtil.fail(message, r.cause());
                return;
            }
            withReplyRun(() -> consumerInterface.accept(r.result()), message);
        };
    }

    public static void withReplyRun(Runnable runnable, Message message) {
        try {
            runnable.run();
        } catch (Exception e) {
            ExceptionUtil.fail(message, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T withReplyCall(Callable<T> runnable, Message message) {
        try {
            return runnable.call();
        } catch (Exception e) {
            ExceptionUtil.fail(message, e);
            throw new RuntimeException(e);
        }
    }

    public static void fail(Message message, Throwable throwable) {
        message.fail(FailureCode.InternalServerError.code, throwable.getMessage());
        System.out.println("FAILING MESSAGE: " + message + " <<>> CAUSE: " + throwable.getMessage());
    }

    public static void logException(Throwable e) {
        System.err.println("Error: " + e.getClass() + " : " + e.getMessage());
    }
}
