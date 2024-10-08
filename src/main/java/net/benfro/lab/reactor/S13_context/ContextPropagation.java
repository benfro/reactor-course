package net.benfro.lab.reactor.S13_context;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ContextPropagation {

    public static void main(String[] args) {
        getSubscribe();
    }

    private static void getSubscribe() {
        getMessage()
            .concatWith(Flux.merge(producer1(), producer2()))
            .contextWrite(context -> context.put("key", "value"))
            .subscribe(DefaultSubscriber.of());
    }

    static Mono<String> getMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("ctx: {}", ctx);
            if (ctx.hasKey("key")) {
                return Mono.just("hello");
            } else {
                return Mono.just("failed");
            }
        });
    }

    static Mono<String> producer1() {
        return Mono.deferContextual(ctx -> {
            log.info("producer1: {}", ctx);
            return Mono.just("PRODUCER_ONE");
        });
    }

    static Mono<String> producer2() {
        return Mono.deferContextual(ctx -> {
            log.info("producer2: {}", ctx);
            return Mono.just("PRODUCER_TWO");
        });
    }
}
