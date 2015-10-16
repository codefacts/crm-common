package io.crm.util;

import io.crm.intfs.Context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by someone on 14/10/2015.
 */
public class ContextImpl implements Context {
    private final Map<String, Object> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public Object remove(Object key) {
        return map.remove(key);
    }

    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    public void clear() {
        map.clear();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public Collection<Object> values() {
        return map.values();
    }

    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public boolean equals(Object o) {
        return map.equals(o);
    }

    public int hashCode() {
        return map.hashCode();
    }

    public String toString() {
        return map.toString();
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    public Object putIfAbsent(String key, Object value) {
        return map.putIfAbsent(key, value);
    }

    public boolean remove(Object key, Object value) {
        return map.remove(key, value);
    }

    public boolean replace(String key, Object oldValue, Object newValue) {
        return map.replace(key, oldValue, newValue);
    }

    public Object replace(String key, Object value) {
        return map.replace(key, value);
    }

    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }

    public Object computeIfPresent(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return map.computeIfPresent(key, remappingFunction);
    }

    public Object compute(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return map.compute(key, remappingFunction);
    }

    public Object merge(String key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return map.merge(key, value, remappingFunction);
    }

    public void forEach(BiConsumer<? super String, ? super Object> action) {
        map.forEach(action);
    }

    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        map.replaceAll(function);
    }

    @Override
    public <T> T get(String key, Class<T> tClass) {
        return tClass.cast(map.get(key));
    }

    @Override
    public <T> T putAndReturn(String key, T value) {
        map.put(key, value);
        return value;
    }
}
