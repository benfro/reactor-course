package net.benfro.lab.reactor.S06_publishertypes.assignment.inventory;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.S06_publishertypes.assignment.Order;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class InventoryService implements Subscriber<Order> {

    private final InventoryData inventoryData;

    public InventoryService() {
        super();
        this.inventoryData = new InventoryData();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("onSubscribe");
    }

    @Override
    public void onNext(Order order) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
