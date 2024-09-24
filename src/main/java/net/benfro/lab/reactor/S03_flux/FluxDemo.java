package net.benfro.lab.reactor.S03_flux;

import java.util.List;
import java.util.stream.Stream;

import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.generator.GenerateUtility;
import reactor.core.publisher.Flux;

public class FluxDemo {

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
    }
}
