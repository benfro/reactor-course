package net.benfro.lab.reactor.S10_batching;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Windowing {

    public static void main(String[] args) {

        eventStream()
//            .window(5)
            .window(Duration.ofSeconds(1))
            .flatMap(flux -> processEvent(flux))
            .subscribe();

        RunUtilities.sleep(50);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
//            .take(10)
            .map(i -> "item-" + i);
    }

    private static Mono<Void> processEvent(Flux<String> innerFlux) {
        return innerFlux
            .doOnNext(i -> System.out.print("*"))
            .doOnComplete(System.out::println)
            .then();

    }

}
