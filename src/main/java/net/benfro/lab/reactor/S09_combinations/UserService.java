package net.benfro.lab.reactor.S09_combinations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class UserService {
    private static final Map<String, Integer> userTable = Map.of(
            "sam", 1,
            "mike", 2,
            "fula", 3
    );

    public static Flux<User> getAllUsers() {
        return Flux.fromIterable(userTable.entrySet())
                .map(e -> new User(e.getValue(), e.getKey()));
    }

    public static Mono<Integer> getUserId(String userName) {
        return Mono.fromSupplier(() -> userTable.get(userName));
    }
}
