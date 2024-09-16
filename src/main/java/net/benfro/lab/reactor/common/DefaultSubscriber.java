package net.benfro.lab.reactor.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
@RequiredArgsConstructor
public class DefaultSubscriber implements Subscriber {

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
        log.info("onSubscribe {}", name);
    }

    @Override
    public void onNext(Object o) {
        log.info("onNext {}", name);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError {}", name, throwable);
    }

    @Override
    public void onComplete() {
        log.info("onComplete {}", name);
    }
}
