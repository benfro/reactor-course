package net.benfro.lab.reactor.S05_fluxoperators;

import java.time.Duration;
import java.util.function.UnaryOperator;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import net.benfro.lab.reactor.common.SubscriberImpl;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxOperators {

    static void exploreHandle() {

        Flux.range(1, 10)
            .handle(
                (item, sink) -> {
                    switch (item) {
                        case 1 -> sink.next(-2);
                        case 4 -> sink.next("apa");
                        case 7 -> sink.error(new RuntimeException("oops"));
                        default -> RunUtilities.noOp();
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

    static void delayElements() {
        var subscriber = new SubscriberImpl<Integer>();
        Flux.range(1, 10)
            .log()
            .delayElements(Duration.ofMillis(1000))
            .subscribe(subscriber);
        subscriber.getSubscription().request(Long.MAX_VALUE);

        RunUtilities.sleep(12);
    }

    static void onErrorReturn() {

        Flux.range(1, 10)
            .map(item -> item == 5 ? item / (item - 5) : item)
            .onErrorReturn(5)
            .subscribe(DefaultSubscriber.of());

        Flux.range(1, 10)
            .map(item -> item == 5 ? item / (item - 5) : item)
            .onErrorReturn(ArithmeticException.class, 5)
            .subscribe(DefaultSubscriber.of());
    }

    static void onErrorResume() {

        Flux.range(1, 10)
            .map(item -> item == 5 ? item / (item - 5) : item)
            .onErrorResume(ex -> Flux.just(55))
            .subscribe(DefaultSubscriber.of());

        Flux.range(1, 10)
            .map(item -> item == 5 ? item / (item - 5) : item)
            .onErrorResume(ex -> Flux.just(55/0))
            .onErrorReturn(5)
            .subscribe(DefaultSubscriber.of());
    }

    static void onErrorComplete() {

        Flux.range(1, 10)
            .map(item -> item == 5 ? item / (item - 5) : item)
            .onErrorComplete()
            .subscribe(DefaultSubscriber.of());
    }

    static void onErrorContinue() {

        Flux.range(1, 10)
            .map(item -> item == 5 ? item / (item - 5) : item)
            .onErrorContinue((ex, obj) -> log.error("It went south with {} because of {}", obj, ex.getMessage()))
            .subscribe(DefaultSubscriber.of());
    }

    static void emptiesBehave() {
        Flux.empty()
            .defaultIfEmpty("I am empty")
            .subscribe(DefaultSubscriber.of());

        Flux.empty()
            .switchIfEmpty(switchMethod())
            .subscribe(DefaultSubscriber.of());
    }

    private static Flux<String> switchMethod() {
        return Flux.just("I am empty too");
    }

    static void timeoutIsOfTheEssence() {
        // .timeout(duration)
        // .timeout(duration, callback publisher)
        // Combine timeout with error handler
        // Least duration timeout rulez! (shorter overshadows longer)
    }

    static void transformUsLord() {
        // transform = reusable group of operators
        UnaryOperator<Flux<Integer>> extraTrans = flux -> flux.doOnRequest(l -> log.info("Requested: {}", l));

        Flux.range(1, 10)
            .delayElements(Duration.ofMillis(1000))
            .transform(flux -> flux.doFirst(() -> log.info("Hi there!!!")).doOnComplete(() -> log.info("We're done")))
            .transform(extraTrans)
            .subscribe(DefaultSubscriber.of());

        RunUtilities.sleep(10);
    }

    public static void main(String[] args) {
//        exploreHandle();
//        doCallbacks();
//        delayElements();
//        onErrorReturn();
//        onErrorResume();
//        onErrorComplete();
//        onErrorContinue();
//        emptiesBehave();
        timeoutIsOfTheEssence();
        transformUsLord();
    }

}
