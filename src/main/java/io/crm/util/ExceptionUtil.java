package io.crm.util;

import io.crm.FailureCode;
import io.crm.intfs.CallableUnchecked;
import io.crm.intfs.ConsumerUnchecked;
import io.crm.intfs.RunnableUnchecked;
import io.vertx.core.AsyncResult;
import io.vertx.core.AsyncResultHandler;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by someone on 26-Jul-2015.
 */
final public class ExceptionUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);

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
            logException(e);
        }
    }

    public static <T> T sallowCall(final CallableUnchecked<T> runnable) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            logException(e);
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

    public static <T> Handler<T> withCatch(final ConsumerUnchecked<T> consumerUnchecked, final ConsumerUnchecked<Throwable> runnable) {
        return val -> {
            try {
                consumerUnchecked.accept(val);
            } catch (final Exception e) {
                try {
                    runnable.accept(e);
                } catch (Exception e1) {
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    }
                    throw new RuntimeException(e);
                }
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Handler<T> withWebHandler(final ConsumerUnchecked<T> consumerUnchecked, final Message message) {
        return val -> {
            try {
                consumerUnchecked.accept(val);
            } catch (final Exception e) {
                ExceptionUtil.fail(message, e);
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Handler<T> withHandler(final ConsumerUnchecked<T> consumerUnchecked, final Message message) {
        return val -> {
            try {
                consumerUnchecked.accept(val);
            } catch (final Exception e) {
                ExceptionUtil.fail(message, e);
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Handler<AsyncResult<T>> withReply(final ConsumerUnchecked<T> consumerUnchecked, final Message message) {
        return r -> {
            if (r.failed()) {
                ExceptionUtil.fail(message, r.cause());
                return;
            }
            withReplyRun(() -> consumerUnchecked.accept(r.result()), message);
        };
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

    public static <T> T withReplyCall(final CallableUnchecked<T> runnable, final Message message) {
        try {
            return runnable.call();
        } catch (final Exception e) {
            ExceptionUtil.fail(message, e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static void fail(final Message message, final Throwable throwable) {
        if (message != null) message.fail(FailureCode.InternalServerError.code, throwable.getMessage());
        LOGGER.error("FAILING_MESSAGE: ", throwable);
    }

    public static void logException(final Throwable e) {
        LOGGER.error("ExceptionUtil: ", e);
    }
}
