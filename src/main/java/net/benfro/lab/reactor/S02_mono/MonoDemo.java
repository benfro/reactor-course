package net.benfro.lab.reactor.S02_mono;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoDemo {

    /**
     * Mono can contain 0 or 1 object
     */
    public static void main(String[] args) {

        Mono.just("Hey world")
            .subscribe(DefaultSubscriber.of());

        Mono.fromSupplier(() -> "Hey ho!")
            .subscribe(DefaultSubscriber.of());

        // Callbacks on success and failure
        Mono.fromCallable(() -> adder(List.of(1, 2, 3)))
            .subscribe(i -> log.info(i + ""),
                err -> log.error("error {}", err.getMessage()),
                () -> log.info("completed"));

        // Error handling
        Mono.fromCallable(() -> adder(List.of(1, 2, 3)) / 0)
            .subscribe(i -> log.info(i + ""),
                err -> log.error("error {}", err.getMessage()),
                () -> log.info("completed"));

    }

    static int adder(List<Integer> list) {
        return list.stream().mapToInt(i -> i).sum();
    }

}
