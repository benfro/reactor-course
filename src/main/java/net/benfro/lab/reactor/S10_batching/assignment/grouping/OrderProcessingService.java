package net.benfro.lab.reactor.S10_batching.assignment.grouping;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OrderProcessingService {

    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PROCESSOR_MAP = Map.of(
        "Kids", kidsProcessing(),
        "Automotive", automotiveProcessing()
    );

    /**
     * Change an item in a Flux
     */
    private static UnaryOperator<Flux<PurchaseOrder>> automotiveProcessing() {
        return flux -> flux
            .map(po -> new PurchaseOrder(po.item(), po.category(), po.price() + 100));
    }

    /**
     * Add an item to a Flux
     */
    private static UnaryOperator<Flux<PurchaseOrder>> kidsProcessing() {
        return flux -> flux
            .flatMap(po -> getFreeKidsOrder(po).flux().startWith(po));
    }

    private static Mono<PurchaseOrder> getFreeKidsOrder(PurchaseOrder order) {
        return Mono.fromSupplier(() -> new PurchaseOrder(
            order.item() + "-FREE",
            order.category(),
            0));
    }

    public static Predicate<PurchaseOrder> canProcess() {
        return po -> PROCESSOR_MAP.containsKey(po.category());
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category) {
        return PROCESSOR_MAP.get(category);
    }
}