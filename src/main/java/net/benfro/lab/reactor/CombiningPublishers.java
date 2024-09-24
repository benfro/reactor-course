package net.benfro.lab.reactor;


import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Slf4j
public class CombiningPublishers {

    public static void main(String[] args) {
            //producer1()
            //        .startWith(-1,0)
            //        .take(2)
            //        .subscribe(DefaultSubscriber.of());

        //demo2();
        demo3();

        Util.sleep(8);
    }

//    Concat
//    concatWit()
//    concatWitValues()
//    Flux.concat(prod1, prod2, ..)
//    Flux.concatDelayError()

//    Merge
//    Order not guaranteed!!
//    Use case: same data from multiple sources

//    Zip
//    Kombination!

    static void demo1() {
        producer1()
                .startWith(-1,0)
                //.take(2)
                .subscribe(DefaultSubscriber.of());
    }

    static void demo2() {
        producer1()
                .startWith(List.of(-2, -1, 0))
                //.take(2)
                .subscribe(DefaultSubscriber.of());
    }

    static void demo3() {
        producer1()
                .startWith(producer2())
                //.take(2)
                .subscribe(DefaultSubscriber.of());
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> log.info("subscribing to producer 1")).
                delayElements(Duration.ofSeconds(2));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .doOnSubscribe(s -> log.info("subscribing to producer 2")).
                delayElements(Duration.ofSeconds(2));
    }

}
