package net.benfro.lab.reactor.S08_backpressure;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.DefaultSubscriber;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class AutoBackPressure {

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");
        // Flux.generate adapts its generation speed according to what the consumer consumes
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
            .publishOn(Schedulers.boundedElastic())
            .map(AutoBackPressure::timeConsumingTask)
            .subscribe(DefaultSubscriber.of());


        RunUtilities.sleep(60);


    }

    private static int timeConsumingTask(int i) {
        RunUtilities.sleep(1);
        return i;
    }
}
