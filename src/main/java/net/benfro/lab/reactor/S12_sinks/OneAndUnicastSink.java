package net.benfro.lab.reactor.S12_sinks;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.reactivestreams.Subscription;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.BaseSubscriberAdapter;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class OneAndUnicastSink {

    static class Answer extends BaseSubscriberAdapter<String> {

        LinkedList<String> stack = Lists.newLinkedList();
        Subscription subscription;

        @Override
        protected void hookOnNext(String value) {
            log.info(value);
            stack.push(value);

//            if (stack.reversed().stream().collect(Collectors.joining(" ")).contains("vacker")) {
            if (value.equals("vacker")) {
                log.info("men det tycker inte jag!");
            }
        }

        @Override
        protected void hookOnComplete() {
            log.info("meeeeeen det tycker inte jag!");
        }

        @Override
        protected void hookOnCancel() {
            log.info("MEN DET TYCKER INTE JAG!");
        }

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            this.subscription = subscription;
            requestUnbounded();
        }
    }

    public static void main(String[] args) {
//        errorHandlerOne();
//        unicastWithAnswer();

        List<@Nullable Object> theList = threadSafeVsNotThreadSafe();

        RunUtilities.sleep(5);

        log.info("Size of list = " + theList.size());

        ;
    }

    private static List<@Nullable Object> threadSafeVsNotThreadSafe() {
        Sinks.Many<String> many = Sinks.many().unicast().onBackpressureBuffer();
        boolean threadSafe = true;
        Flux<String> flux = many.asFlux();
        List<@Nullable Object> theList = Lists.newArrayList();
        flux.subscribe(theList::add);

        for (int i = 0; i < 1000; i++) {
            int j = i;
            if (!threadSafe) {
                CompletableFuture.runAsync(() -> {
                    many.tryEmitNext("item " + j + 1);
                });
            } else {
                CompletableFuture.runAsync(() -> {
                    many.emitNext("item " + j + 1, (signal, error) -> {
                        return error.equals(Sinks.EmitResult.FAIL_NON_SERIALIZED);
                    });
                });
            }
        }
        return theList;
    }

    private static void unicastWithAnswer() {
        // Unicast sink, can only handle one Subscriber
        Sinks.Many<String> many = Sinks.many().unicast().onBackpressureBuffer();

        Flux<String> flux = many.asFlux();
        flux
//            .doOnComplete(() -> log.info("done"))
            .subscribe(new Answer());

        many.tryEmitNext("Du tycker");
        many.tryEmitNext("du");
        many.tryEmitNext("är");
        many.tryEmitNext("vacker");
//        many.tryEmitComplete();
    }

    private static void errorHandlerOne() {
        Sinks.One<Object> one = Sinks.one();
        one.asMono().subscribe(System.out::println);

        one.tryEmitValue("Hello");
        one.emitValue("World", (signal, result) -> {
            log.info("signal: {} result: {}", signal, result);
            return false;
        });
    }
}
