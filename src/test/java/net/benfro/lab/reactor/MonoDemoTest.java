package net.benfro.lab.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MonoDemoTest {

    MonoDemo instance = new MonoDemo();

    @Test
    void testSubscribe() {
        instance.getMono("hej")
                .subscribe(i -> log.info(i));
    }

    @Test
    void testSupplier() {
        Mono.fromSupplier(() -> instance.adder(List.of(1, 2, 3)))
                .subscribe(i -> log.info(i + ""));
    }

    @Test
    void testCallable() {
        Mono.fromCallable(() -> instance.adder(List.of(1, 2, 3)))
                .subscribe(i -> log.info(i + ""),
                        err -> log.error("error {}", err.getMessage()),
                        () -> log.info("completed"));
    }

    @Test
    void testError() {
        Mono.fromCallable(() -> instance.adder(List.of(1, 2, 3)) / 0)
                .subscribe(i -> log.info(i + ""),
                        err -> log.error("error {}",err.getMessage()),
                        () -> log.info("completed"));
    }
}