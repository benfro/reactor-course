package net.benfro.lab.reactor.S08_backpressure;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class LimitRate {

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");

        var producer = Flux.generate(
                () -> 1,
                (state, sink) -> {
                    log.info("generating {}", state);
                    sink.next(state);
                    return ++state;
                }
            )
            .cast(Integer.class)
            .subscribeOn(Schedulers.parallel());

        producer
            .limitRate(5) // tell producer!
            .publishOn(Schedulers.boundedElastic())
            .map(LimitRate::timeConsumingTask)
            .subscribe(DefaultSubscriber.of());


        RunUtilities.sleep(60);


    }

    private static int timeConsumingTask(int i) {
        log.info("{}", i);
        RunUtilities.sleep(1);
        return i;
    }
}
