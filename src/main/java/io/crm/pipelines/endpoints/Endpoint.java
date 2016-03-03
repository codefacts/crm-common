package io.crm.pipelines.endpoints;

import io.crm.promise.intfs.Promise;
import io.vertx.core.eventbus.Message;

/**
 * Created by shahadat on 3/3/16.
 */
public interface Endpoint<T> {
    void process(Message<T> message);
}
