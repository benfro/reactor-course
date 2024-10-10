package net.benfro.lab.reactor.S07_schedulers;


import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
Downward effect!!
 */
@Slf4j
public class PublishOnDemo {

    public static void main(String[] args) {
        publishOn();

    }

    static void blockingEventLoopIssueFix() {
        var flux = Flux.create(sink -> {
                for (int i = 1; i < 3; i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                }
                sink.complete();
            })
            .doOnNext(v -> log.info("value {}", v))
            .doFirst(() -> log.info("first1"))
            .publishOn(Schedulers.boundedElastic()) // Upwards
            .doFirst(() -> log.info("first2"));

        Runnable r1 = () -> flux
            .subscribe(DefaultSubscriber.of("subscribeOn 1"));
        Thread.ofPlatform().start(r1);
    }


    static void publishOn() {
        var flux = Flux.create(sink -> {
                for (int i = 1; i < 3; i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                }
                sink.complete();
            })
            .doOnNext(v -> log.info("value {}", v))
            .doFirst(() -> log.info("first1"))
            .publishOn(Schedulers.boundedElastic()) // Upwards
            .doFirst(() -> log.info("first2"));

        Runnable r1 = () -> flux
            .subscribe(DefaultSubscriber.of("subscribeOn 1"));
        Thread.ofPlatform().start(r1);
    }
}
