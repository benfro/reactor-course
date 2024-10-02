package net.benfro.lab.reactor.S12_sinks;

import java.util.LinkedList;
import java.util.stream.Collectors;

import org.reactivestreams.Subscription;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.BaseSubscriberAdapter;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class SinksDemo {

    static class Answer extends BaseSubscriberAdapter<String> {

        LinkedList<String> stack = Lists.newLinkedList();
        Subscription subscription;

        @Override
        protected void hookOnNext(String value) {
            log.info(value);
            stack.push(value);

            if (stack.reversed().stream().collect(Collectors.joining(" ")).contains("vacker")) {
//                log.info("men det tycker inte jag!");
//                subscription.cancel();
            }
        }

        @Override
        protected void hookOnComplete() {
            log.info("men det tycker inte jag!");
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
    
     static <T> Sinks.One<T> one() {
        return Sinks.one();
    }

    public static void main(String[] args) {
//        errorHandlerOne();

        Sinks.Many<String> many = Sinks.many().unicast().onBackpressureBuffer();

        Flux<String> flux = many.asFlux();
        flux
//            .doOnComplete(() -> log.info("done"))
            .subscribe(new Answer());

        many.tryEmitNext("Du tycker");
        many.tryEmitNext("du");
        many.tryEmitNext("är");
        many.tryEmitNext("vacker");
        many.tryEmitComplete();

        RunUtilities.sleep(5);

    }

    private static void errorHandlerOne() {
        Sinks.One<Object> one = one();
        one.asMono().subscribe(System.out::println);

        one.tryEmitValue("Hello");
        one.emitValue("World", (signal, result) -> {
            log.info("signal: {} result: {}", signal, result);
            return false;
        });
    }
}
