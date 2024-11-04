package net.benfro.lab.reactor.S14_unittesting;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

public class MonoProducer {

    public Mono<String> withContext() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")) {
                return Mono.just("Welcome");
            }
            return Mono.error(new RuntimeException("Liar!"));
        });
    }

    public UnaryOperator<Flux<String>> someComplexStuff() {
        return flux -> flux
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase)
                .map(s -> s + ":" + s.length());
    }
}

