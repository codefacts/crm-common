package io.crm.util;

import io.crm.QC;
import io.crm.util.exceptions.InvalidArgumentException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sohan on 8/1/2015.
 */
public class Util {
    public static final String mongoDateFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(mongoDateFormatString);
        }
    };

    public static Document toDocument(JsonObject jsonObject) {
        return toDocument(new Document(), jsonObject);
    }

    public static List toDocumentArray(JsonArray jsonObject) {
        return toDocumentArray(new ArrayList<>(), jsonObject);
    }

    public static List<Document> toDocumentList(final Collection<JsonObject> jsonObjects) {
        ArrayList<Document> documentList = new ArrayList<>();
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

    public static Date parseMongoDate(String isoString) throws ParseException {
        return mongoDateFormat().parse(isoString);
    }

    public static void validateMongoDate(String iso_date) {
        try {
            mongoDateFormat().parse(iso_date);
        } catch (ParseException e) {
            throw new InvalidArgumentException("ISO DATE " + iso_date + " is invalid.");
        }
    }

    public static JsonObject toMongoDate(String iso_string) {
        validateMongoDate(iso_string);
        return new JsonObject().put(QC.$date, iso_string);
    }

    public static JsonObject toMongoDate(Date date) {
        return new JsonObject().put(QC.$date, toIsoString(date));
    }

    public static JsonObject toMongoDate(Date date, Date defaultValue) {
        return new JsonObject().put(QC.$date, toIsoString(date, defaultValue));
    }

    public static String toIsoString(final Date date) {
        return mongoDateFormat().format(date) + "Z";
    }

    private static String toIsoString(Date date, Date defaultValue) {
        try {
            return mongoDateFormat().format(date);
        } catch (Exception ex) {
            if (defaultValue != null) {
                return mongoDateFormat().format(date);
            }
            return "";
        }
    }

    public static Date parseMongoDate(JsonObject jsonObject, Date defaultValue) {
        try {
            return parseMongoDate(jsonObject.getString(QC.$date));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static Date parseMongoDate(JsonObject jsonObject) throws ParseException {
        return parseMongoDate(jsonObject.getString(QC.$date));
    }

    public static DateFormat mongoDateFormat() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }

    public static Long id(JsonObject area, String field) {
        final Object value = area.getValue(field);
        if (value instanceof JsonObject) {
            JsonObject json = (JsonObject) value;
            return json.getLong(QC.id);
        }
        return area.getLong(field);
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

    public static boolean isEmptyOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }
}
