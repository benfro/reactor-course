package net.benfro.lab.reactor.S08_backpressure;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class BackpressureStrategies {

    public static void main(String[] args) {

        var producer = Flux.create(sink -> {
                for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                    log.info("generating {}", i);
                    sink.next(i);
                    RunUtilities.sleepMs(50);
                }
                sink.complete();
            },
            // 6. You can define all strategies already here
                FluxSink.OverflowStrategy.LATEST)
            .cast(Integer.class)
            .subscribeOn(Schedulers.parallel());

        producer
            // 1. Buffer strategy, good for evening out spikes in producer rate
//            .onBackpressureBuffer()
            // 2. Error strategy, cancelling upstream, error downstream
//            .onBackpressureError()
            // 3. Sort of combining 1 and 2 - when buffer full => error
//            .onBackpressureBuffer(10)
            // 4. Drops generated stuff as long as it cannot process them
//            .onBackpressureDrop()
            // 5. Keeps the latest produced item for consumption
//            .onBackpressureLatest()
            .log()
            .limitRate(1)
            .publishOn(Schedulers.boundedElastic())
            .map(BackpressureStrategies::timeConsumingTask)
            .subscribe();

        RunUtilities.sleep(60);
    }

    private static int timeConsumingTask(int i) {
        log.info("received {}", i);
        RunUtilities.sleep(1);
        return i;
    }



}
