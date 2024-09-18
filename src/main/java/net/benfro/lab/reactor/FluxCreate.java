package net.benfro.lab.reactor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.assignment3.FileReaderImpl;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.SubscriberImpl;
import net.benfro.lab.reactor.common.Util;
import net.benfro.lab.reactor.helper.NameGenerator;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxCreate {

    static void createFluxWithSink() {
        Flux.create(sink -> {
            sink.next("Hello World");
            sink.next("Hello Mars");
            sink.complete();
        }).subscribe(DefaultSubscriber.of());
    }

    static void generateNames(int numNames) {
        var generator = new NameGenerator();
        Flux.create(generator)
            .subscribe(DefaultSubscriber.of());
        generator.generate(numNames);
    }

    static void notThreadSafe() {
        var list = new ArrayList<Integer>();
        Runnable r = () -> {
            for (var i = 0; i < 1000; i++) {
                list.add(i);
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(r);
        }
        Util.sleep(10);
        log.info("Size of list is {}", list.size());
    }

    static void threadSafe() {
        var list = new ArrayList<String>();
        var generator = new NameGenerator();
        Flux.create(generator)
            .subscribe(list::add);

        Runnable r = () -> {
            for (var i = 0; i < 1000; i++) {
                generator.generate(1);
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(r);
        }
        Util.sleep(10);
        log.info("Size of list is {}", list.size());
    }

    static void produceEarly() {
        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                fluxSink.next(Util.faker.name().firstName());
            }
            fluxSink.complete();
        }).subscribe(subscriber);
        requestItems(subscriber);
    }

    static void produceOnDemand() {
        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
            fluxSink.onRequest(request -> {
                for (int i = 0; i < request; i++) {
                    fluxSink.next(Util.faker.name().firstName());
                }
            });
        }).subscribe(subscriber);
        requestItems(subscriber);
    }

    private static void requestItems(SubscriberImpl subscriber) {
        Util.sleep(2);
        subscriber.getSubscription().request(2);
        Util.sleep(2);
        subscriber.getSubscription().request(2);
        Util.sleep(2);
        subscriber.getSubscription().cancel();
    }

    static void takeMethods() {
        Flux.range(1, 10)
            .take(3)
//            .takeWhile(i -> i % 3 == 0)
//            .takeUntil(i -> i % 3 == 0)
            .subscribe(DefaultSubscriber.of());
    }

    static void generateExternalState() {
        AtomicInteger ai = new AtomicInteger(1);
        Flux.generate(sink -> {
                sink.next(ai.getAndIncrement());
            })
            .take(10)
            .subscribe(DefaultSubscriber.of());
    }



    static void generateInternalState() {
        Flux.generate(
                () -> 1,
                (state, sink) -> {
                    sink.next(state++);
                    return state;
                })
            .take(10)
            .subscribe(DefaultSubscriber.of());
    }

    static void assignment3() {
        var subscriber = new SubscriberImpl();
        var fileService = new FileReaderImpl();
        fileService.read("rows.txt")
            .subscribe(subscriber);
        requestItems(subscriber);
    }

    public static void main(String[] args) {
//        createFluxWithSink();
//        generateNames(100);
//        notThreadSafe();
//        threadSafe();
//        produceEarly();
//        produceOnDemand();
//        takeMethods();
//        generateExternalState();
//        generateInternalState();
        assignment3();
    }
}
