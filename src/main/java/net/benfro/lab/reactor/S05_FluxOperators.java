package net.benfro.lab.reactor;

import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.Util;
import reactor.core.publisher.Flux;

public class S05_FluxOperators {

    static void exploreHandle() {

        Flux.range(1, 10)
            .handle(
                (item, sink) -> {
                    switch (item) {
                        case 1 -> sink.next(-2);
                        case 4 -> sink.next("apa");
                        case 7 -> sink.error(new RuntimeException("oops"));
                        default -> Util.noOp();
                    }
                }
            ).subscribe(DefaultSubscriber.of());
    }

    public static void main(String[] args) {
        exploreHandle();
    }

}
