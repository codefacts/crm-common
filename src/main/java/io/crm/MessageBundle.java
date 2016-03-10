package io.crm;

import com.google.common.collect.ImmutableMap;
import io.crm.util.Util;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.crm.util.Util.or;
import static io.crm.util.Util.publish;

/**
 * Created by shahadat on 2/28/16.
 */
public class MessageBundle {
    private final Map<String, String> bundle;
    private static final Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]\\w*?\\}");

    public MessageBundle(String str) {
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        new JsonObject(str).getMap().forEach((k, v) -> {
            builder.put(k, v.toString());
        });
        bundle = builder.build();
    }

    public String translate(String messageCode, JsonObject json) {
        return translateMessage(or(bundle.get(or(messageCode, "")), ""), json);
    }

    private String translateMessage(String template, JsonObject json) {
        Matcher matcher = pattern.matcher(template);

        final StringBuilder builder = new StringBuilder();
        int start = 0;
        while (matcher.find()) {
            final String key = matcher.group().replace("${", "").replace("}", "");
            builder
                .append(template.substring(start, matcher.start()))
                .append(json.getValue(key, ""))
            ;
            start = matcher.end();
        }

        builder.append(template.substring(start));

        return builder.toString();
    }

    public static void main(String... args) {

    }
}
