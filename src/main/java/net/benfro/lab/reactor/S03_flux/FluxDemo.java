package net.benfro.lab.reactor.S03_flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import net.benfro.lab.reactor.common.generator.GenerateUtility;
import reactor.core.publisher.Flux;

public class FluxDemo {

    /**
     * Flux can contain 0..n objects
     */
    public static void main(String[] args) {

        Flux.fromStream(Stream.of(1, 2, 3))
            .subscribe(DefaultSubscriber.of());

        Flux.fromIterable(List.of(1, 2, 3))
            .subscribe(DefaultSubscriber.of());

        Flux.fromArray(List.of(1, 2, 3).toArray())
            .subscribe(DefaultSubscriber.of());

        Flux.range(1, 10)
            .subscribe(DefaultSubscriber.of());

        Flux.range(1, 10)
            .map(i -> GenerateUtility.faker.artist().name())
            .log("log-artists")
            .subscribe(DefaultSubscriber.of());

        Flux.interval(Duration.ofMillis(500))
            .take(5)
            .map(i -> GenerateUtility.faker.commerce().productName())
            .subscribe(DefaultSubscriber.of());

        Flux.merge(just10(), just3())
            .subscribe(DefaultSubscriber.of("QQQ"));

        Flux.just(1, 2, 3)
            .concatWith(just10())
            .subscribe(DefaultSubscriber.of("Q"));

        Flux.combineLatest(just10(), just3(), (a, b) -> a + b)
            .subscribe(DefaultSubscriber.of("XXX"));

        Flux.combineLatest(just3(), just10(), (a, b) -> a + b)
            .subscribe(DefaultSubscriber.of("YYY"));

        RunUtilities.sleep(20);
    }

    static Flux<Integer> just10() {
        return Flux.range(1, 10);
    }

    static Flux<Integer> just3() {
        return Flux.just(1, 2, 3);
    }
}
