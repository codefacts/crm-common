package io.crm.intfs;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by someone on 14/10/2015.
 */
public interface Context extends Map<String, Object> {
    public <T> T get(String key, Class<T> tClass);

    public <T> T putAndReturn(String key, T value);
}
