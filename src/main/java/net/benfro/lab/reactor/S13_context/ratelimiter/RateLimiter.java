package net.benfro.lab.reactor.S13_context.ratelimiter;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RateLimiter {

    private static final Map<String, Integer> categoryAttempts = Collections.synchronizedMap(Maps.newHashMap());

    static {
        refresh();
//        refreshMap();
    }

    private static void refreshMap() {
        categoryAttempts.put("standard", 2);
        categoryAttempts.put("prime", 3);
    }

    static <T> Mono<T> limitCalls() {
        return Mono.deferContextual(ctx -> {
            Boolean allowCall = ctx.<String>getOrEmpty("category")
                .map(RateLimiter::canAllow)
                .orElse(false);
            return allowCall ? Mono.empty() : Mono.error(new RuntimeException("Out of limit"));
        });
    }

    private static boolean canAllow(String category) {
        Integer attempts = categoryAttempts.getOrDefault(category, 0);
        if (attempts > 0) {
            categoryAttempts.put(category, attempts - 1);
            return true;
        }
        return false;
    }

    private static void refresh() {
        Flux.interval(Duration.ofSeconds(5)) // Will emit first after 5 seconds - empty map
            .startWith(0L) // Make sure we are in business from the start
            .subscribe(i -> refreshMap());
    }
}
