package net.benfro.lab.reactor;

import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.helper.NameGenerator;
import reactor.core.publisher.Flux;

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

    public static void main(String[] args) {
        createFluxWithSink();
        generateNames(100);
    }
}
