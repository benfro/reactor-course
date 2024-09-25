package net.benfro.lab.reactor.S09_combinations;

import net.benfro.lab.reactor.generator.GenerateUtility;

import java.util.List;
import java.util.Map;

public class OrderService {

    public static final Map<Integer, List<Order>> orderTable = Map.of(
            1,
            List.of(
                    new Order(1, pn(), ni()),
                    new Order(1, pn(), ni())
            ),
            2,
            List.of(
                    new Order(2, pn(), ni()),
                    new Order(2, pn(), ni())
            ),
            3,
            List.of(
                    new Order(3, pn(), ni()),
                    new Order(3, pn(), ni())
            )

    );

    static String pn() {
        return GenerateUtility.faker.commerce().productName();
    }

    static Integer ni() {
        return GenerateUtility.faker.random().nextInt(10, 100);
    }


}
