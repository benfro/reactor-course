package net.benfro.lab.reactor.S12_sinks;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class MulticastSink {

    public static void main(String[] args) {
//        multicast();
//        warmupBehavior();
//        multicastDirectBestEffort();
//        allOrNothing();
        replayForLateSubscribers();

        RunUtilities.sleep(10);
    }

    private static void replayForLateSubscribers() {
        // Unbounded
        Sinks.Many<Object> many = Sinks.many().replay().all();
//        Sinks.Many<Object> many = Sinks.many().replay().limit(2);
//        Sinks.Many<Object> many = Sinks.many().replay().limit(Duration.ofMillis(200));

        Flux<Object> flux = many.asFlux();

        flux.subscribe(DefaultSubscriber.of("knut"));
        flux.subscribe(DefaultSubscriber.of("gustav"));

        many.tryEmitNext("Glad");
        many.tryEmitNext("såsom");
        many.tryEmitNext("fågeln");

        RunUtilities.sleep(2);
        flux.subscribe(DefaultSubscriber.of("kevin"));
        many.tryEmitNext("i morgonstunden!");
    }

    /**
     * If not all get it - nobody get it
     */
    private static void allOrNothing() {
        System.setProperty("reactor.bufferSize.small", "16");
        Sinks.Many<Object> many = Sinks.many().multicast().directAllOrNothing();

        Flux<Object> flux = many.asFlux();

        flux.subscribe(DefaultSubscriber.of("knut"));
        // Gustav is a slow subscriber
        flux.delayElements(Duration.ofMillis(200)).subscribe(DefaultSubscriber.of("gustav"));

        for (int i = 1; i <= 100; i++) {
            Sinks.EmitResult emitResult = many.tryEmitNext(i);
            log.info("item: {}, result: {}", i, emitResult);
        }
    }

    /**
     * How to mitigate a slow consumer
     */
    private static void multicastDirectBestEffort() {
        System.setProperty("reactor.bufferSize.small", "16");
        // Fails miserably
//        Sinks.Many<Object> many = Sinks.many().multicast().onBackpressureBuffer();
        // Fix? At least Knut gets correct data
        Sinks.Many<Object> many = Sinks.many().multicast().directBestEffort();

        Flux<Object> flux = many.asFlux();

        flux.subscribe(DefaultSubscriber.of("knut"));
        // Gustav is a slow subscriber
//        flux.delayElements(Duration.ofMillis(200)).subscribe(DefaultSubscriber.of("gustav"));
        // Gustav is getting the stuff in his own pace
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(DefaultSubscriber.of("gustav"));

        for (int i = 1; i <= 100; i++) {
            Sinks.EmitResult emitResult = many.tryEmitNext(i);
            log.info("item: {}, result: {}", i, emitResult);
        }
    }

    /**
     * Well, everybody needs a warmup, right?
     */
    private static void warmupBehavior() {
        Sinks.Many<Object> many = Sinks.many().multicast().onBackpressureBuffer();

        Flux<Object> flux = many.asFlux();

        many.tryEmitNext("Glad");
        many.tryEmitNext("såsom");
        many.tryEmitNext("fågeln");

        RunUtilities.sleep(2);

        flux.subscribe(DefaultSubscriber.of("knut"));
        flux.subscribe(DefaultSubscriber.of("gustav"));
        flux.subscribe(DefaultSubscriber.of("kevin"));

        many.tryEmitNext("i morgonstunden!");
    }

    /**
     * Send to many subscribers
     */
    private static void multicast() {
        // NOT unbounded buffer!!! (when multicast)
        Sinks.Many<Object> many = Sinks.many().multicast().onBackpressureBuffer();

        Flux<Object> flux = many.asFlux();

        flux.subscribe(DefaultSubscriber.of("knut"));
        flux.subscribe(DefaultSubscriber.of("gustav"));

        many.tryEmitNext("Glad");
        many.tryEmitNext("såsom");
        many.tryEmitNext("fågeln");

        RunUtilities.sleep(2);
        flux.subscribe(DefaultSubscriber.of("kevin"));
        many.tryEmitNext("i morgonstunden!");
    }
}
