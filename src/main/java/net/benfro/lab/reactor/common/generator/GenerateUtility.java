package net.benfro.lab.reactor.common.generator;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class GenerateUtility {

    public static final Faker faker = new Faker();

    public static Flux<Integer> range(int end) {
        return Flux.range(1, end);
    }

}
