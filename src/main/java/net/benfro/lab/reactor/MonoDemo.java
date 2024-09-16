package net.benfro.lab.reactor;

import reactor.core.publisher.Mono;

import java.util.List;

public class MonoDemo {

    Mono<String> getMono(String string) {
        return Mono.just(string);
    }

    Mono<String> getEmptyMono() {
        return Mono.empty();
    }

    int adder(List<Integer> list) {
        return list.stream().mapToInt(i -> i).sum();
    }

}
