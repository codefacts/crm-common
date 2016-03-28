package io.crm.sql;

import io.vertx.core.json.JsonObject;

import java.util.Collection;
import java.util.List;

/**
 * Created by shahadat on 3/27/16.
 */
final public class SqlUtils {

    public static String create(String tableName, Collection<String> fieldNames) {
        StringBuilder builder = new StringBuilder();

        builder
            .append("insert into ")
            .append(tableName)
            .append(" ")
            .append("(");

        final String COMMA = ", ";

        fieldNames.forEach(name -> {
            builder.append(name).append(COMMA);
        });

        builder.delete(builder.lastIndexOf(COMMA), builder.length());

        builder
            .append(")")
            .append(" values ")
            .append("(")
        ;

        for (int i = 0; i < fieldNames.size(); i++) {
            builder.append("?").append(COMMA);
        }

        builder.delete(builder.lastIndexOf(COMMA), builder.length());

        builder
            .append(")")
        ;

        return builder.toString();
    }
}
