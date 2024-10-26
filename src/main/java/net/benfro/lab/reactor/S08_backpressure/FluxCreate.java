package net.benfro.lab.reactor.S08_backpressure;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FluxCreate {

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");
        // Flux.create isn't as smart as Flux.generate
        var producer = Flux.create( sink -> {
                for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                    RunUtilities.sleepMs(50);
                }
                sink.complete();
            })
            .cast(Integer.class)
            .subscribeOn(Schedulers.parallel());

        producer
            .publishOn(Schedulers.boundedElastic())
            .map(FluxCreate::timeConsumingTask)
            .subscribe();

        RunUtilities.sleep(60);
    }

    private static int timeConsumingTask(int i) {
        log.info("received {}", i);
        RunUtilities.sleep(1);
        return i;
    }

}
