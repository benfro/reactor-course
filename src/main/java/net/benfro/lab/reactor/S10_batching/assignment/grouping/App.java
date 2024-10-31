package net.benfro.lab.reactor.S10_batching.assignment.grouping;

import java.time.Duration;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;

@Slf4j
public class App {

    private static final List<String> CATEGORIES =
        List.of("Kids", "Automotive");

    /**
     * Filter - Group - Process
     */
    public static void main(String[] args) {
        orderStream()
            // Filter
            .filter(OrderProcessingService.canProcess())
            // Group
            .groupBy(PurchaseOrder::category)
            // Process
            .flatMap(gf -> gf.transform(OrderProcessingService.getProcessor(gf.key())))
            .subscribe(DefaultSubscriber.of());

        RunUtilities.sleep(60);
    }

    static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(350))
            .map(i -> PurchaseOrder.generate());
    }

}
