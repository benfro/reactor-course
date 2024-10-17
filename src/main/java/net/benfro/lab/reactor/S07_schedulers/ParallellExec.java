package net.benfro.lab.reactor.S07_schedulers;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ParallellExec {

    /**
     * Prefer non-blocking I/O instead of parallell - always better!
     */
    public static void main(String[] args) {

        Flux.range(1, 10)
            .parallel()
            .runOn(Schedulers.parallel())
            .map(i -> process(i))
            .sequential()
            .subscribe(DefaultSubscriber.of());

        RunUtilities.sleep(3);
    }

    static int process(int i) {
        log.info("time consuming task {}", i);
        RunUtilities.sleep(1);
        return i * 2;
    }
}

