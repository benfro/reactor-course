package net.benfro.lab.reactor.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
@RequiredArgsConstructor
public class DefaultSubscriber<T> implements Subscriber<T> {

    public static DefaultSubscriber of() {
        return new DefaultSubscriber();
    }

    public static DefaultSubscriber of(String name) {
        return new DefaultSubscriber(name);
    }

    @Getter
    private final String name;

    DefaultSubscriber() {
        this.name = "";
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        log.info("onSubscribe {}", name);
    }

    @Override
    public void onNext(T t) {
        log.info("onNext: {} {}", t.toString(), name );
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError {}", name, throwable);
    }

    @Override
    public void onComplete() {
        log.info("onComplete {}", name);
    }

//    @Override
//    public void onSubscribe(Subscription subscription) {
//        log.info("onSubscribe {}", name);
//    }
//
//    @Override
//    public void onNext(T o) {
//        log.info("onNext: {} {}", o.toString(), name );
//    }
//
//    @Override
//    public void onError(Throwable throwable) {
//        log.error("onError {}", name, throwable);
//    }
//
//    @Override
//    public void onComplete() {
//        log.info("onComplete {}", name);
//    }
}
