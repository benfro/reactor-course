package net.benfro.lab.reactor.S14_unittesting;

import net.benfro.lab.reactor.common.generator.GenerateUtility;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxProducer {
    public Flux<Integer> createFlux() {
        return Flux.just(1, 2, 3);
    }

    public Flux<Integer> createRange() {
        return Flux.range(1, 50);
    }

    public Flux<Integer> createRandom() {
        return Flux.range(1, 50)
                .map(i -> GenerateUtility.faker.random().nextInt(1, 100));
    }

    record Book(int id, String title, String author) {}

    public Flux<Book> createBooks() {
        return Flux.range(1, 3)
                .map(i -> new Book(i, GenerateUtility.faker.book().title(), GenerateUtility.faker.book().author()));
    }

    public Flux<Integer> createSlowRange() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(10));
    }

    public Flux<Integer> createTimeoutRange() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
    }


}
