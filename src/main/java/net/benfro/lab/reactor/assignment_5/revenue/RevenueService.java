package net.benfro.lab.reactor.assignment_5.revenue;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.assignment_5.Order;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class RevenueService implements Subscriber<Order> {

    private final RevenueData data;

    public RevenueService() {
        super();
        data = new RevenueData();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("revenueService onSubscribe");
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
}
