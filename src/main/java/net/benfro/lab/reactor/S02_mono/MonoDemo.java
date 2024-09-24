package net.benfro.lab.reactor.S02_mono;

import java.util.List;

import reactor.core.publisher.Mono;

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
