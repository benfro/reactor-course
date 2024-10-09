package net.benfro.lab.reactor.S11_repeat;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import net.benfro.lab.reactor.common.generator.GenerateUtility;
import net.benfro.lab.reactor.common.generator.NameGenerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
public class S11_Repeat {

    // TODO Lec 164 - watch it!
    public static void main(String[] args) {

        new NameGenerator();

        Mono<String> name = Mono.fromCallable(() -> GenerateUtility.faker.name().firstName());

        name
            .subscribe(DefaultSubscriber.of("Mono"));

        name
            .repeat()
            .take(10)
            .subscribe(DefaultSubscriber.of("Flux"));

        Mono.fromCallable(() -> GenerateUtility.faker.name().username())
            .repeat(3)
            .subscribe(DefaultSubscriber.of("Flux 2"));

        Mono.fromCallable(() -> GenerateUtility.faker.name().username())
            .repeat(() -> GenerateUtility.faker.random().nextInt(0, 100) % 3 != 0)
            .subscribe(DefaultSubscriber.of("Flux 3"));


        Mono.fromCallable(() -> GenerateUtility.faker.name().username())
            .repeatWhen(flux  -> flux.delayElements(Duration.ofSeconds(2)).take(3))
            .subscribe(DefaultSubscriber.of("Flux 4"));

        RunUtilities.sleep(10);

        Mono.fromCallable(() -> GenerateUtility.faker.name().username())
            .retry(2)
            .subscribe(DefaultSubscriber.of("Flux 6"));

        Mono.fromCallable(() -> GenerateUtility.faker.name().username())
            .retryWhen(Retry.max(2)
                .doBeforeRetry(rs -> log.info("Retrying...{}", rs.totalRetries())))
            .subscribe(DefaultSubscriber.of("Flux 7"));

        // Repeat the repeat!!!
        Flux.just("a")
            .repeat(1)
            .repeat(2)
            .subscribe(DefaultSubscriber.of("Flux 9"));
    }
}
