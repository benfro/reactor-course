package net.benfro.lab.reactor.S10_batching;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;

@Slf4j
public class Buffering {

    public static void main(String[] args) {
        demo4();

        RunUtilities.sleep(10);
    }

    private static void demo1() {
        eventStream()
            .buffer()
            .subscribe(DefaultSubscriber.of());
    }


    private static void demo2() {
        eventStream()
            .buffer(3)
            .subscribe(DefaultSubscriber.of());
    }

    private static void demo3() {
        eventStream()
            .buffer(Duration.ofMillis(500))
            .subscribe(DefaultSubscriber.of());
    }

    private static void demo4() {
        eventStream()
            .bufferTimeout(3, Duration.ofMillis(700))
            .subscribe(DefaultSubscriber.of());
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(1000))
            .take(10)
//            .concatMap(i -> Flux.never())
            .map(i -> "item-" + i);
    }


}
