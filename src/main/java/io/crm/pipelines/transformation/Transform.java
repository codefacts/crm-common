package io.crm.pipelines.transformation;

/**
 * Created by shahadat on 3/1/16.
 */
public interface Transform<T, R> {
    R transform(T val);
}
