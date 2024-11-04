package net.benfro.lab.reactor.S14_unittesting;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FluxProducerTest {

    @Test
    void testSequence1() {
        StepVerifier.create(new FluxProducer().createFlux())
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectComplete()
                .verify();
    }

    @Test
    void testSequence2() {
        StepVerifier.create(new FluxProducer().createFlux())
                .expectNext(1,2,3)
                .expectComplete()
                .verify();
    }

    @Test
    void testOnlyOne() {
        StepVerifier.create(new FluxProducer().createFlux(), 1)
                .expectNext(1)
                .thenCancel()
                .verify();
    }

    @Test
    void testRange1() {
        StepVerifier.create(new FluxProducer().createRange())
                .expectNext(1,2,3)
                .expectNextCount(47)
                .expectComplete()
                .verify();
    }

    @Test
    void testRange2() {
        StepVerifier.create(new FluxProducer().createRange())
                .expectNext(1,2,3)
                .expectNextCount(22)
                .expectNext(26,27,28)
                .expectNextCount(22)
                .expectComplete()
                .verify();
    }


    @Test
    void testRandom1() {
        StepVerifier.create(new FluxProducer().createRandom())
                .expectNextMatches(i -> i > 0 && i < 100)
                .expectNextCount(49)
                .expectComplete()
                .verify();
    }

    @Test
    void testRandom2() {
        StepVerifier.create(new FluxProducer().createRandom())
                .thenConsumeWhile(i -> i > 0 && i <= 100)
                .expectComplete()
                .verify();
    }

    @Test
    void testBooks1() {
        StepVerifier.create(new FluxProducer().createBooks())
                .assertNext(i -> assertEquals(1, i.id()))
                .thenConsumeWhile(i -> Objects.nonNull(i.title()))
                .expectComplete()
                .verify();
    }

    @Test
    void testBooks2() {
        StepVerifier.create(new FluxProducer().createBooks().collectList())
                .assertNext(list -> assertEquals(3, list.size()))
                .expectComplete()
                .verify();
    }

    @Test
    void testSlowRange1() {
        StepVerifier.withVirtualTime(() -> new FluxProducer().createSlowRange())
                .thenAwait(Duration.ofSeconds(51))
                .expectNext(1,2,3, 4, 5)
                .expectComplete()
                .verify();
    }

    @Test
    void testSlowRange2() {
        StepVerifier.withVirtualTime(() -> new FluxProducer().createSlowRange())
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(9))
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(1)
                .thenAwait(Duration.ofSeconds(40))
                .expectNext(2,3, 4, 5)
                .expectComplete()
                .verify();
    }

    @Test
    void testTimeout() {
        StepVerifier.create(new FluxProducer().createTimeoutRange())
                .expectNext(1,2,3,4,5)
                .expectComplete()
                .verify(Duration.ofMillis(500));
//                .verify();
    }
}