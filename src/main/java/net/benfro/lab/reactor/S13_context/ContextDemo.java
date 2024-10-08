package net.benfro.lab.reactor.S13_context;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import reactor.core.publisher.Mono;

@Slf4j
public class ContextDemo  {


    public static void main(String[] args) {

//        getSubscribe();
        bottomToTop();

    }

    private static void getSubscribe() {
        getMessage()
            .contextWrite(context -> context.put("key", "value"))
            .subscribe(DefaultSubscriber.of());
    }

    private static void append() {
        getMessage()
            .contextWrite(context -> context.put("key", "value").put("key2", "value2").put("key3", "value3"))
            .subscribe(DefaultSubscriber.of());
    }

    /**
     * Context written from bottom to top!!
     */
    private static void bottomToTop() {
        getMessage()
            .contextWrite(context -> context.delete("key"))
            .contextWrite(context -> context.put("key", "value").put("key2", "value2").put("key3", "value3"))
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
}
