package net.benfro.lab.reactor.common;

import org.reactivestreams.Subscription;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public class DefaultSubscriber<T> extends BaseSubscriberAdapter<T> {

    public static <T> DefaultSubscriber<T> of() {
        return new DefaultSubscriber<>();
    }

    public static <T> DefaultSubscriber<T> of(String name) {
        return new DefaultSubscriber<>(name);
    }

    private final String name;

    DefaultSubscriber() {
        this.name = "";
    }

    @Override
    public void hookOnSubscribe(Subscription subscription) {
        super.upstream().request(Long.MAX_VALUE);
        log.info("subscriber [{}] in 'onSubscribe'", name);
    }

    @Override
    public void hookOnNext(T t) {
        log.info("subcriber [{}] in 'onNext' - element is :: {}", name, t.toString());
    }

    @Override
    public void hookOnError(Throwable throwable) {
        log.error("subcriber [{}] in 'onError'", name, throwable);
    }

    @Override
    public void hookOnComplete() {
        log.info("subscriber [{}] in 'onComplete'", name);
    }
}
