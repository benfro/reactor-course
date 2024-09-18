package net.benfro.lab.reactor.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public class DefaultSubscriber<T> implements Subscriber<T> {

    public static <T> DefaultSubscriber<T> of() {
        return new DefaultSubscriber<T>();
    }

    public static <T> DefaultSubscriber<T> of(String name) {
        return new DefaultSubscriber<T>(name);
    }

    private final String name;
    private Subscription subscription;

    DefaultSubscriber() {
        this.name = "";
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
        log.info("subscriber [{}] in 'onSubscribe'", name);
    }

    @Override
    public void onNext(T t) {
        log.info("subcriber [{}] in 'onNext' - element is :: {}", name, t.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("subcriber [{}] in 'onError'", name, throwable);
    }

    @Override
    public void onComplete() {
        log.info("subscriber [{}] in 'onComplete'", name);
    }

}
