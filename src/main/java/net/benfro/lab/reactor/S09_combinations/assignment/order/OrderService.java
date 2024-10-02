package net.benfro.lab.reactor.S09_combinations.assignment.order;

import net.benfro.lab.reactor.common.generator.GenerateUtility;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OrderService {

    public static final Map<Integer, List<Order>> orderTable = Map.of(
            1,
            List.of(
                    new Order(1, pName(), rand()),
                    new Order(1, pName(), rand())
            ),
            2,
            List.of(
                    new Order(2, pName(), rand()),
                    new Order(2, pName(), rand())
            ),
            3,
            List.of(
                    new Order(3, pName(), rand()),
                    new Order(3, pName(), rand())
            )

    );

    static String pName() {
        return GenerateUtility.faker.commerce().productName();
    }

    static Integer rand() {
        return GenerateUtility.faker.random().nextInt(10, 100);
    }

    public static Flux<Order> getUserOrders(Integer userId) {
        return Flux.fromIterable(orderTable.get(userId))
            .delayElements(Duration.ofMillis(500));
    }


}
