package io.crm.pipelines;

import io.crm.promise.Promises;
import io.crm.promise.intfs.Defer;
import io.crm.util.ExceptionUtil;
import io.crm.util.Util;
import io.crm.util.touple.MutableTpl1;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;

import java.util.Objects;

/**
 * Created by shahadat on 3/2/16.
 */
final public class Pipelines {

    private static final String $$$SEQ_COMPLETE = "$$$SEQ_COMPLETE";

    public static <T> rx.Observable<Message<T>> bridgeAndInitiate(final EventBus eventBus, final String dest, Object message, DeliveryOptions deliveryOptions) {

        final Observable<Message<T>> emitter = Observable.create(
            subscriber -> {

                eventRequestLoop(eventBus, dest, message, deliveryOptions, subscriber, null);
            });

        return emitter;
    }

    private static <T> void eventRequestLoop(final EventBus eventBus, final String dest, Object message, DeliveryOptions deliveryOptions, Subscriber<? super Message<T>> subscriber, DeliveryOptions replyDeliveryOptions) {
        Objects.requireNonNull(deliveryOptions);
        Objects.requireNonNull(replyDeliveryOptions);

        Util.<T>send(eventBus, dest, message, deliveryOptions)
            .complete(p -> {
                if (!subscriber.isUnsubscribed()) {
                    if (p.isSuccess()) {
                        subscriber.onNext(p.get());
                    } else {
                        subscriber.onError(p.error());
                    }
                }
            })
            .filter(msg -> !msg.headers().contains($$$SEQ_COMPLETE) && !subscriber.isUnsubscribed())
            .then(msg -> reqLoop(msg, deliveryOptions, subscriber, replyDeliveryOptions));
    }

    private static <T> void reqLoop(Message<T> msg, DeliveryOptions deliveryOptions, Subscriber<? super Message<T>> subscriber, DeliveryOptions replyDeliveryOptions) {
        if (!subscriber.isUnsubscribed()) {
            msg.<T>reply(null, replyDeliveryOptions, asyncResult -> {


                if (!subscriber.isUnsubscribed()) {
                    if (!msg.headers().contains($$$SEQ_COMPLETE)) {
                        if (asyncResult.succeeded()) {
                            subscriber.onNext(asyncResult.result());
                            reqLoop(msg, deliveryOptions, subscriber, replyDeliveryOptions);
                        } else {
                            subscriber.onError(asyncResult.cause());
                        }
                    } else {
                        subscriber.onCompleted();
                    }
                }

            });
        }
    }

    public static <T> void bridgeFrom(Message message, Observable<T> src, DeliveryOptions replyDeliveryOptions) {
        Objects.requireNonNull(replyDeliveryOptions);

        PublishSubject<T> subject = PublishSubject.create();
        Subscription subscription = src.subscribe(subject);

        MutableTpl1<Subscription> tpl1 = new MutableTpl1<>();

        tpl1.t1 = src.subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                message.reply(null, replyDeliveryOptions.addHeader($$$SEQ_COMPLETE, ""));
                tpl1.t1.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtil.fail(message, e);
                tpl1.t1.unsubscribe();
            }

            @Override
            public void onNext(T t) {
                final Defer<Message> defer = Promises.defer();
                message.reply(t, replyDeliveryOptions, asyncResult -> {
                    if (asyncResult.failed()) defer.fail(asyncResult.cause());
                    else defer.complete(asyncResult.result());
                });

                defer.complete();
            }
        });
    }
}
