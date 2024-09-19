package net.benfro.lab.reactor;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.SubscriberImpl;
import net.benfro.lab.reactor.common.Util;
import reactor.core.publisher.Flux;

@Slf4j
public class S05_FluxOperators {

    static void exploreHandle() {

        Flux.range(1, 10)
            .handle(
                (item, sink) -> {
                    switch (item) {
                        case 1 -> sink.next(-2);
                        case 4 -> sink.next("apa");
                        case 7 -> sink.error(new RuntimeException("oops"));
                        default -> Util.noOp();
                    }
                }
            ).subscribe(DefaultSubscriber.of());
    }

    static void doCallbacks() {
        Flux.<Integer>create(fluxSink -> {
                log.info("=> producer begins");
                for (int i = 0; i < 4; i++) {
                    fluxSink.next(i);
                }
                fluxSink.complete();
                // fluxSink.error(new RuntimeException("oops"));
                log.info("=> producer ends");
            })
            .doOnComplete(() -> log.info("doOnComplete-1"))
            .doFirst(() -> log.info("doFirst-1"))
            .doOnNext(item -> log.info("doOnNext-1: {}", item))
            .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
            .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
            .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
            .doOnTerminate(() -> log.info("doOnTerminate-1")) // complete or error case
            .doOnCancel(() -> log.info("doOnCancel-1"))
            .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))
            .doFinally(signal -> log.info("doFinally-1: {}", signal)) // finally irrespective of the reason
            // .take(2)
            .doOnComplete(() -> log.info("doOnComplete-2"))
            .doFirst(() -> log.info("doFirst-2"))
            .doOnNext(item -> log.info("doOnNext-2: {}", item))
            .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))
            .doOnRequest(request -> log.info("doOnRequest-2: {}", request))
            .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))
            .doOnTerminate(() -> log.info("doOnTerminate-2")) // complete or error case
            .doOnCancel(() -> log.info("doOnCancel-2"))
            .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))
            .doFinally(signal -> log.info("doFinally-2: {}", signal)) // finally irrespective of the reason
            //.take(4)
            .subscribe(DefaultSubscriber.of());
    }

    static void delay() {
        var subscriber = new SubscriberImpl<Integer>();
        Flux.range(1, 10)
            .log()
            .delayElements(Duration.ofMillis(1000))
            .subscribe(subscriber);
        subscriber.getSubscription().request(Long.MAX_VALUE);

        Util.sleep(12);
    }

    // Error handling Pt 1
    // .onErrorReturn(value)
    // .onErrorReturn(Exception, value)
    // Pt 2
    // .onErrorResume(exception -> fallback method)
    // .onErrorResume(exception -> fallback method2)
    // .onErrorReturn(2) <== catches errors in the error handler above
    // Pt 3
    // .onErrorComplete()
    // Pt 4
    // .onErrorContinue((exc, obj) -> doit)

    // .defaultIfEmpty(default value)
    // .switchIfEmpty( fallback)
    // .timeout(duration)
    // .timeout(duration, callback publisher)
    // Combine timeout with error handler
    // Least duration timeout rulez! (shorter overshadows longer)

    // transform = reusable group of operators
    // <T> UnaryOperator<Flux<T>> - return flux -> flux..


    public static void main(String[] args) {
//        exploreHandle();
//        doCallbacks();
        delay();
    }

}
