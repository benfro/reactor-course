package net.benfro.lab.reactor.S09_combinations.assignment;

import java.util.Map;

import reactor.core.publisher.Mono;

public class PaymentService {

    public static final Map<Integer, Integer> userBalanceTable = Map.of(
        1, 100,
        2, 200,
        3, 300
    );

    public static final Mono<Integer> getBalance(Integer userId) {
        return Mono.fromSupplier(() -> userBalanceTable.get(userId));
    }
}
