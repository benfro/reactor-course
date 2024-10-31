package net.benfro.lab.reactor.S10_batching;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

@Slf4j
public class GroupingBy {
    /**
     * Multiple fluxes open - 'window' only one at a time
     */

    public static void main(String[] args) {

        Flux.range(1, 10)
            .delayElements(Duration.ofSeconds(1))
//            .map(i -> i * 2)
//            .startWith(1)
            .groupBy(o -> o % 2)
            .flatMap(groupedFlux -> processEvents(groupedFlux))
            .subscribe();

        RunUtilities.sleep(50);
    }

    /**
     * Only called two times, for each possible key 0 and 1
     * Both fluxes open until the whole shabang completes
     */
    private static Mono<Void> processEvents(GroupedFlux<Integer, Integer> groupedFlux) {
        log.info("received flux for {}", groupedFlux.key());
        return groupedFlux
            .doOnNext(i -> log.info("QQQ key: {}, item: {}", groupedFlux.key(), i))
//            .doOnComplete(() -> log.info("{} complete", groupedFlux.key()))
            .then();
    }

}
