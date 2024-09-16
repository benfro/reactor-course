package net.benfro.lab.reactor;

import java.util.List;

import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.Util;
import reactor.core.publisher.Flux;

public class FluxDemo {

    public static void main(String[] args) {

        Flux.fromStream(List.of(1, 2, 3).stream())
            .subscribe(DefaultSubscriber.of());

        Flux.fromIterable(List.of(1, 2, 3))
            .subscribe(DefaultSubscriber.of());

        var arr = new int[]{1, 2, 3};

        Flux.fromArray(List.of(1, 2, 3).toArray())
            .subscribe(DefaultSubscriber.of());

        Flux.range(1, 10)
            .subscribe(DefaultSubscriber.of());

        Flux.range(1, 10)
            .map(i -> Util.faker.artist().name())
            .log("log-artists")
            .subscribe(DefaultSubscriber.of());
    }
}
