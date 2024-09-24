package net.benfro.lab.reactor.S06_publishertypes;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;

@Slf4j
public class HotAndColdPublisher {


    public static void main(String[] args) {
//        coldSubscriberBehaviour();
        makingItHot();

    }

    static void makingItHot() {
        var flux = movieStreaming()
            .take(5)
            .delayElements(Duration.ofMillis(1000))
            .share();

        var mike = DefaultSubscriber.of("mike");
        var john = DefaultSubscriber.of("john");

        flux.subscribe(mike);
        RunUtilities.sleep(2);
        flux.subscribe(john);
        RunUtilities.sleep(10);
    }

    static void coldSubscriberBehaviour() {
        var flux = movieStreaming()
            .take(5)
            .delayElements(Duration.ofMillis(1000));

        var mike = DefaultSubscriber.of("mike");
        var john = DefaultSubscriber.of("john");

        flux.subscribe(mike);
        RunUtilities.sleep(2);
        flux.subscribe(john);
        RunUtilities.sleep(10);
    }

    private static Flux<String> movieStreaming() {
        return Flux.generate(
            () -> 1,
            (state, sink) -> {
                log.info("streaming service emitting scene {}", state);
                sink.next(("watching scene " + state));
                return ++state;
            }
        );
    }

}
