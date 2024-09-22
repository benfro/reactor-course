package net.benfro.lab.reactor.assignment_6.revenue;

import java.time.Duration;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.assignment_6.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class RevenueService implements Subscriber<Order>
    , Publisher<Mono<String>>
{

    @Getter
    private final RevenueData data;

    public RevenueService() {
        super();
        data = new RevenueData();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("revenueService onSubscribe");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Order order) {
        log.info("revenueService handling {}", order);
        data.updateAdd(order.category(), order.value());
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("revenueService onComplete");

    }

    @Override
    public void subscribe(Subscriber<? super Mono<String>> subscriber) {
    }

    public Flux<String> publishReport() {
//        return Flux.generate(
//            () -> data.getData(),
//            (map, sink) -> {
//                sink.next(data.report());
//                return map;
//            }
//        );

//        return Flux.<String>create(sink -> {
//                sink.onRequest(request -> {
//                    log.info("revenueService request {}", request);
//                    data.logState();
//                    sink.next(data.report());
//                });
//            }
//        ).delayElements(Duration.ofSeconds(5));

        return Flux.interval(Duration.ofSeconds(2))
            .map(i -> data.report());
    }
}
