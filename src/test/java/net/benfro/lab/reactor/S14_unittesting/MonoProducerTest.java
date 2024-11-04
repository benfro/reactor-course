package net.benfro.lab.reactor.S14_unittesting;

import net.benfro.lab.reactor.common.DefaultSubscriber;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.test.publisher.TestPublisher;
import reactor.util.context.Context;

import java.text.DecimalFormat;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class MonoProducerTest {

    @Test
    void testContext1() {
        var options = StepVerifierOptions.create().withInitialContext(Context.of("user", "sarah"));
        StepVerifier.create(new MonoProducer().withContext(), options)
                .expectNext("Welcome")
                .expectComplete()
                .verify();
    }

    @Test
    void testContext2() {
        var options = StepVerifierOptions.create().withInitialContext(Context.empty());
        StepVerifier.create(new MonoProducer().withContext(), options)
                .expectErrorMessage("Liar!")
//                .expectComplete()
                .verify();
    }



    @Test
    void publisherProcessorTest1() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        // Doesn't work - no subscriber
//        publisher.emit("hi", "hello");

        StepVerifier.create(flux.transform(new MonoProducer().someComplexStuff()))
                .then(() -> publisher.emit("hi", "hello"))
                .expectNext("HI:2")
                .expectNext("HELLO:5")
                .expectComplete()
                .verify();

//        flux.subscribe(DefaultSubscriber.of());

//        publisher.next("a", "b");
//        publisher.complete();

    }

    @Test
    void publisherProcessorTest2() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        StepVerifier.create(flux.transform(new MonoProducer().someComplexStuff()))
                .then(() -> publisher.emit("a", "b", "c"))
                .expectComplete()
                .verify();
    }
}