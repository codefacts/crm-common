package io.crm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import io.crm.Events;
import io.crm.QC;
import io.crm.intfs.CallableUnchecked;
import io.crm.promise.Promises;
import io.crm.promise.intfs.Defer;
import io.crm.promise.intfs.Promise;
import io.crm.util.exceptions.InvalidArgumentException;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.bson.Document;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by sohan on 8/1/2015.
 */
final public class Util {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final JsonArray EMPTY_JSON_ARRAY = new JsonArray(Collections.EMPTY_LIST);
    public static final JsonObject EMPTY_JSON_OBJECT = new JsonObject(Collections.EMPTY_MAP);
    public static final String mongoDateFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(mongoDateFormatString);
        }
    };

    public static Document toDocument(final JsonObject jsonObject) {
        return toDocument(new Document(), jsonObject);
    }

    public static List toDocumentArray(final JsonArray jsonObject) {
        return toDocumentArray(new ArrayList<>(), jsonObject);
    }

    public static List<Document> toDocumentList(final Collection<JsonObject> jsonObjects) {
        final ArrayList<Document> documentList = new ArrayList<>();
        jsonObjects.forEach(obj -> documentList.add(toDocument(obj)));
        return documentList;
    }

    public static Document toDocument(final Document doc, final JsonObject jsonObject) {
        for (final Map.Entry<String, Object> e : jsonObject) {
            final String key = e.getKey();
            final Object value = e.getValue();
            if (value instanceof JsonObject) {
                doc.append(key, toDocument(new Document(), (JsonObject) value));
            } else if (value instanceof JsonArray) {
                doc.append(key, toDocumentArray(new ArrayList<>(), (JsonArray) value));
            }
            doc.append(key, value);
        }
        return doc;
    }

    public static List toDocumentArray(final List list, final JsonArray jsonArray) {
        for (final Object obj : jsonArray) {
            if (obj instanceof JsonObject) {
                list.add(toDocument(new Document(), (JsonObject) obj));
            } else if (obj instanceof JsonArray) {
                list.add(toDocumentArray(new ArrayList<>(), (JsonArray) obj));
            }
            list.add(obj);
        }
        return list;
    }

    public static Date parseMongoDate(final String isoString, final Date defaultValue) {
        try {
            return mongoDateFormat().parse(isoString);
        } catch (final ParseException ex) {
            return defaultValue;
        }
    }

    public static Date parseMongoDate(final String isoString) throws ParseException {
        return mongoDateFormat().parse(isoString);
    }

    public static void validateMongoDate(final String iso_date) {
        try {
            mongoDateFormat().parse(iso_date);
        } catch (ParseException e) {
            throw new InvalidArgumentException("ISO DATE " + iso_date + " is invalid.");
        }
    }

    public static JsonObject toMongoDate(final String iso_string) {
        validateMongoDate(iso_string);
        return new JsonObject().put(QC.$date, iso_string);
    }

    public static JsonObject toMongoDate(final Date date) {
        return new JsonObject().put(QC.$date, toIsoString(date));
    }

    public static JsonObject toMongoDate(final Date date, final Date defaultValue) {
        return new JsonObject().put(QC.$date, toIsoString(date, defaultValue));
    }

    public static String toIsoString(final Date date) {
        return mongoDateFormat().format(date) + "Z";
    }

    private static String toIsoString(final Date date, final Date defaultValue) {
        try {
            return mongoDateFormat().format(date);
        } catch (Exception ex) {
            if (defaultValue != null) {
                return mongoDateFormat().format(date);
            }
            return "";
        }
    }

    public static Date parseMongoDate(final JsonObject jsonObject, final Date defaultValue) {
        try {
            return parseMongoDate(jsonObject.getString(QC.$date));
        } catch (ParseException | NullPointerException ex) {
            return defaultValue;
        }
    }

    public static Date parseMongoDate(final JsonObject jsonObject) throws ParseException {
        return parseMongoDate(jsonObject.getString(QC.$date));
    }

    public static DateFormat mongoDateFormat() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }

    public static Long id(final Object value) {
        if (value instanceof JsonObject) {
            final JsonObject json = (JsonObject) value;
            return json.getLong(QC.id);
        }
        return ((Number) value).longValue();
    }

    public static final String getOrDefault(final Enum src, final String defaultValue) {
        return src == null ? defaultValue : src.name();
    }

    public static final <T> T getOrDefault(final T src, final T defaultValue) {
        return src == null ? defaultValue : src;
    }

    public static void main(String... args) throws ParseException {
        System.out.println(parseMongoDate(new JsonObject().put(QC.$date, "2015-05-17T00:00:00Z")));
    }

    public static JsonObject updateObject(JsonObject jsonObject) {
        jsonObject.remove(QC.id);
        return new JsonObject().put(QC.$set, jsonObject);
    }

    public static String trim(String string) {
        return string == null ? "" : string.trim();
    }

    public static boolean isEmptyOrNullOrSpaces(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static List<String> listEvents() {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (Field f : Events.class.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                try {
                    builder.add(f.get("").toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return builder.build();
    }

    public static <T> T as(Object obj, Class<T> tClass) {
        return tClass.cast(obj);
    }

    public static JsonObject toJsonObject(final Object obj) {
        return toJsonObject(obj, null);
    }

    public static JsonObject toJsonObject(final Object obj, final JsonObject jsonObject) {
        return obj instanceof JsonObject ? (JsonObject) obj : obj instanceof Map ? new JsonObject((Map<String, Object>) obj) : jsonObject;
    }

    public static JsonArray toJsonArray(final Object obj) {
        return toJsonArray(obj, null);
    }

    public static JsonArray toJsonArray(final Object obj, final JsonArray jsonArray) {
        return obj instanceof JsonArray ? (JsonArray) obj : obj instanceof List ? new JsonArray((List) obj) : jsonArray;
    }

    public static List<Long> listIds(List<JsonObject> list) {
        return list.stream().map(v -> v.getLong(QC.id)).collect(Collectors.toList());
    }

    public static List<Long> listIds(JsonArray array) {
        return array.stream().map(v -> toJsonObject(v).getLong(QC.id)).collect(Collectors.toList());
    }

    public static List<String> listIdValuePairs(List<JsonObject> list) {
        return list.stream().map(v -> String.format("[ID: %d, Name: %s]", v.getLong(QC.id), v.getString(QC.name))).collect(Collectors.toList());
    }

    public static List<String> listIdValuePairs(JsonArray array) {
        return array.stream().map(v -> String.format("[ID: %d, Name: %s]", toJsonObject(v).getLong(QC.id), toJsonObject(v).getString(QC.name))).collect(Collectors.toList());
    }

    public static JsonObject toJsonRecursive(final Throwable ex) {
        if (ex == null) return null;
        JsonObject jsonObject = exceptionToJson(ex);
        Throwable e = ex.getCause();
        for (; ; ) {
            if (e == null) break;
            final JsonObject js = exceptionToJson(e);
            jsonObject
                    .put(QC.cause, js);
            jsonObject = js;
            e = e.getCause();
        }
        return jsonObject;
    }

    private static JsonObject exceptionToJson(Throwable ex) {
        final ImmutableList.Builder<JsonObject> builder = ImmutableList.builder();
        for (StackTraceElement e : ex.getStackTrace()) {
            builder.add(
                    new JsonObject()
                            .put(QC.className, e.getClassName())
                            .put(QC.fieldName, e.getFileName())
                            .put(QC.methodName, e.getMethodName())
                            .put(QC.lineNumber, e.getLineNumber())
            );
        }
        return
                new JsonObject()
                        .put(QC.fullName, ex.getClass().getName())
                        .put(QC.simpleName, ex.getClass().getSimpleName())
                        .put(QC.message, ex.getMessage())
                        .put(QC.stackTrace, builder.build())
                ;
    }

    public static <T> Promise<Message<T>> send(final EventBus eventBus, final String dest, Object message) {
        final Defer<Message<T>> defer = Promises.defer();
        eventBus.send(dest, message, (AsyncResult<Message<T>> r) -> {
            if (r.failed()) {
                defer.fail(r.cause());
            } else {
                defer.complete(r.result());
            }
        });
        return defer.promise();
    }

    public static <T> Handler<AsyncResult<T>> makeDeferred(Defer<T> defer) {
        return r -> {
            if (r.failed()) defer.fail(r.cause());
            else defer.complete(r.result());
        };
    }

    public static <T> Promise<T> executeBlocking(Vertx vertx, CallableUnchecked<T> callableUnchecked) {
        final Defer<T> defer = Promises.defer();
        vertx.executeBlocking(f -> {
            try {
                defer.complete(callableUnchecked.call());
            } catch (Exception ex) {
                ex.printStackTrace();
                f.fail(ex);
            }
        }, Util.makeDeferred(defer));
        return defer.promise();
    }

    public static JsonObject pagination(final int page, final int size, final long total, final int length) {
        return
                new JsonObject()
                        .put(QC.page, page)
                        .put(QC.size, size)
                        .put(QC.total, total)
                        .put(QC.length, length)
                ;
    }

    public static <T> boolean eq(final T t1, final T t2) {
        return t1.equals(t2);
    }

    public static <T, R> R apply(final T t, final Function<T, R> function) {
        return function.apply(t);
    }

    public static <T> T accept(final T t, final Consumer<T> consumer) {
        consumer.accept(t);
        return t;
    }

    public static <R> R call(final Callable<R> rCallable) {
        try {
            return rCallable.call();
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> T copy(T model, Class<T> tClass) throws Exception {
        final byte[] bytes = mapper.writeValueAsBytes(model);
        final T copy = mapper.readValue(bytes, tClass);
        return copy;
    }

    public static void run(final Runnable runnable) {
        runnable.run();
    }

    public static String toString(Enum i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Character i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Byte i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Short i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Integer i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Long i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Float i) {
        return i == null ? "" : i + "";
    }

    public static String toString(Double i) {
        return i == null ? "" : i + "";
    }
}
