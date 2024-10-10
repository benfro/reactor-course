package net.benfro.lab.reactor.S07_schedulers;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SubscribeOnDemo {

    /*
     * subscribeOn => upstream
     * publishOn => downstream
     */
    public static void main(String[] args) {
//        defaultBehavior();
//        doWorkInSeparateThread();
//        subscriben();
//        multipleSubscribeOn();

        RunUtilities.sleep(2);
    }

    /*
     * Virtual threads
     * Set system property "reactor.schedulers.defaultBoundedElasticOnVirtualThreads" to "true"
     * Test with "Thread.currentThread().isVirtual()"
     */

    /*
     * The purpose of Scheduler.immediate() is a functional one -
     * business as usual in the current thread.
     */

    /*
     * When dealing with external components sometimes the thread pool you
     * request doesn't get used. This is because the lib author has prescribed another.
     * So your request can be denied if applicable.
     * Below request is not denied though.
     */
    private static void multipleSubscribeOn() {
        var flux = Flux.create(sink -> {
                for (int i = 1; i < 3; i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                }
                sink.complete();
            })
            .subscribeOn(Schedulers.newParallel("benfro"))
            .doOnNext(v -> log.info("value {}", v))
            .doFirst(() -> log.info("first1"))
            .subscribeOn(Schedulers.boundedElastic()) // Upwards
            .doFirst(() -> log.info("first2"));

        Runnable r1 = () -> flux
            .subscribe(DefaultSubscriber.of("subscribeOn 1"));

        Thread.ofPlatform().start(r1);
    }

    /*
     * "subscribeOn" work upwards from its declaration
     * If you start it in a runnable it is that thread which use boundedElastic pool
     */
    private static void subscribeOn() {
        Runnable r1 = () -> createFlux2()
            .subscribe(DefaultSubscriber.of("subscribeOn 1"));

        Runnable r2 = () -> createFlux2()
            .subscribe(DefaultSubscriber.of("subscribeOn 2"));

        Thread.ofPlatform().start(r1);
        Thread.ofPlatform().start(r2);
    }

    /*
     * Runs in own thread!
     */
    private static void doWorkInSeparateThread() {
        var flux = createFlux();
        Runnable r = () -> flux.subscribe(DefaultSubscriber.of("runnable"));
        Thread.ofPlatform().start(r);
    }

    /*
     * Subscribers have their own sink
     * Subscribers thread do all the work
     * Default is "main" thread
     */
    static void defaultBehavior() {
        var flux = createFlux();
        flux.subscribe(DefaultSubscriber.of("sub1"));
        flux.subscribe(DefaultSubscriber.of("sub2"));

    }

    static Flux<Object> createFlux() {
        return Flux.create(sink -> {
                for (int i = 1; i < 3; i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                }
                sink.complete();
            })
            .doOnNext(v -> log.info("value {}", v));
    }

    static Flux<Object> createFlux2() {
        return Flux.create(sink -> {
                for (int i = 1; i < 3; i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                }
                sink.complete();
            })
            .doOnNext(v -> log.info("value {}", v))
            .doFirst(() -> log.info("first1"))
            .subscribeOn(Schedulers.boundedElastic()) // Upwards
            .doFirst(() -> log.info("first2"));
    }

}
