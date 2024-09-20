package net.benfro.lab.reactor.assignment_5;

import net.benfro.lab.reactor.common.AbstractHttpClient;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class OrderService {

    static class OrderServiceWebClient extends AbstractHttpClient {
        public Flux<Order> getOrder() {
            return this.httpClient.get()
                    .uri("/demo02/stock/stream")
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

        orderService.allOrders().subscribe(System.out::println);
    }

}
