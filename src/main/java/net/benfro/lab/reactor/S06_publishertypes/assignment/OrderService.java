package net.benfro.lab.reactor.S06_publishertypes.assignment;

import java.time.Duration;

import net.benfro.lab.reactor.S06_publishertypes.assignment.revenue.RevenueService;
import net.benfro.lab.reactor.common.RunUtilities;
import net.benfro.lab.reactor.webclient.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class OrderService {

    static class OrderServiceWebClient extends AbstractHttpClient {
        public Flux<Order> getOrder() {
            return this.httpClient.get()
                    .uri("/demo04/orders/stream")
                    .responseContent()
                    .asString()
                    .map(Order::fromString);
        }
    }

    public Flux<Order> allOrders() {
        return new OrderServiceWebClient().getOrder();
    }

    public static void main(String[] args) {
        var orderService = new OrderService();

        // RevenueService subscribe to OrderService
        var revenueService = new RevenueService();
        orderService.allOrders().publish().autoConnect()
            .delayElements(Duration.ofMillis(500))
            .subscribe(revenueService);

        // RevenueService publishes its report
//        var subscriber = new SubscriberImpl();
        revenueService.publishReport()
//            .delaySubscription(Duration.ofMillis(4900))
//            .delayElements(Duration.ofSeconds(5))
//            .doOnNext(v -> revenueService.getData().logState())
                .subscribe(System.out::println);
            ;
//        subscriber.getSubscription().request(Long.MAX_VALUE);
        RunUtilities.sleep(20);
    }

}
