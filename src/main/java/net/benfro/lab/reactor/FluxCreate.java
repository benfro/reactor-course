package net.benfro.lab.reactor;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
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

        Util.sleep(2);
        subscriber.getSubscription().request(2);
        Util.sleep(2);
        subscriber.getSubscription().request(2);
        Util.sleep(2);
        subscriber.getSubscription().cancel();
    }

    public static void main(String[] args) {
//        createFluxWithSink();
//        generateNames(100);
//        notThreadSafe();
//        threadSafe();
        produceEarly();
    }
}
