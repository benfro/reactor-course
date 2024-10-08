package net.benfro.lab.reactor.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SubscriberImpl<T> implements Subscriber<T> {

    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(T email) {
        log.info("received: {}", email);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error", throwable);
    }

    @Override
    public void onComplete() {
        log.info("completed!");
    }
}
