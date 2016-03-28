package io.crm.util;

import io.crm.ErrorCodes;
import io.crm.intfs.CallableUnchecked;
import io.crm.intfs.RunnableUnchecked;
import io.vertx.core.AsyncResultHandler;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by someone on 26-Jul-2015.
 */
final public class ExceptionUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);
    private static final String DEV_MODE = "dev-mode";

    public static void toRuntime(final RunnableUnchecked runnableUnchecked) {
        try {
            runnableUnchecked.run();
        } catch (final Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static <T> T toRuntimeCall(final CallableUnchecked<T> runnable) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static void sallowRun(final RunnableUnchecked runnableUnchecked) {
        try {
            runnableUnchecked.run();
        } catch (final Exception e) {
            logSallowEx(e);
        }
    }

    public static <T> T sallowCall(final CallableUnchecked<T> runnable) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            logSallowEx(e);
        }
        return null;
    }

    public static <T> void then(final CallableUnchecked<T> callableUnchecked, final AsyncResultHandler<T> asyncResultHandler) {
        try {
            final T t = callableUnchecked.call();
            asyncResultHandler.handle(AsyncUtil.success(t));
        } catch (final Exception ex) {
            asyncResultHandler.handle(AsyncUtil.fail(ex));
        }
    }

    public static void withReplyRun(final RunnableUnchecked runnableUnchecked, final Message message) {
        try {
            runnableUnchecked.run();
        } catch (final Exception e) {
            ExceptionUtil.fail(message, e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static void fail(final Message message, final Throwable throwable) {
        Objects.requireNonNull(message, "ExceptionUtil.fail: argument message can't be null.");
        Objects.requireNonNull(throwable, "ExceptionUtil.fail: argument throwable can't be null.");

        final String uuid = UUID.randomUUID().toString();

        message.fail(ErrorCodes.SERVER_ERROR.code(), errorMessage(ErrorCodes.SERVER_ERROR.code(), message, throwable, uuid));
        LOGGER.error("FAILING_MESSAGE: " +
            new JsonObject()
                .put("uuid", uuid)
                .put("address", message.address())
                .put("body", message.body())
                .put("headers", message.headers().toString()).encodePrettily(), throwable);
    }

    private static String errorMessage(int code, Message message, Throwable throwable, String uuid) {
        if (System.getProperty(DEV_MODE) != null) {
            return
                new JsonObject()
                    .put("exception", throwable.getClass().toString())
                    .put("cause", throwable.getCause() == null ? "" : throwable.getCause().toString())
                    .put("message", throwable.getMessage())
                    .put("code", code)
                    .put("uuid", uuid)
                    .put("timestamp", Util.formatDateTime(new Date(), ""))
                    .put("address", message.address())
                    .put("body", message.body())
                    .put("headers", message.headers().toString()).encodePrettily();
        } else {
            return "Server Error. Error code: " + code + ", uuid: " + uuid + ", timestamp: " + Util.formatDateTime(new Date(), "");
        }
    }

    public static void logSallowEx(final Throwable e) {
        LOGGER.error("EXCEPTION_SALLOWED: ", e);
    }

    public static void main(String... args) {
        MultiMap entries = MultiMap.caseInsensitiveMultiMap();
        System.out.println(entries.add("kk", "kk")
            .add("kk", "tt")
            .add("ss", "ss"));
    }
}
