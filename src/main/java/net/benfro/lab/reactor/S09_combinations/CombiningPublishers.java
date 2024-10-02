package net.benfro.lab.reactor.S09_combinations;


import java.time.Duration;
import java.util.List;
import java.util.function.UnaryOperator;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;

@Slf4j
public class CombiningPublishers {

    public static void main(String[] args) {

        //demo2();
        demo1();

        RunUtilities.sleep(8);
    }

//    Concat
//    concatWith()
//    concatWithValues()
//    Flux.concat(prod1, prod2, ..)
//    Flux.concatDelayError()
//    startWith <==> concat

//    Merge
//    Order not guaranteed!!
//    Use case: same data type from multiple sources

//    Zip
//    Combine Flux<A> and Flux<B> into Flux<A,B>
//    Kombination!

    // FlatMap - access the inner Producer!
    //
    //

    static void demo1() {
        producer1()
            .startWith(-1, 0)
            .transform(addStartWith(1, 2, 3, 4, 5))
            //.take(2)
            .subscribe(DefaultSubscriber.of());
    }

    private static <T> UnaryOperator<Flux<T>> addStartWith(T item, T... items) {
        return flux -> flux.startWith(items).startWith(item);
//        return flux -> flux.doOnSubscribe(i -> log.info(""));
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
