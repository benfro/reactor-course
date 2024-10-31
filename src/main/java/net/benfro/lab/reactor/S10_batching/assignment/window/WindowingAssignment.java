package net.benfro.lab.reactor.S10_batching.assignment.window;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;

@Slf4j
public class WindowingAssignment {
    public static void main(String[] args) {

        var counter = new AtomicInteger(0);
        var fileNameFormat = "src/main/resources/windowAssignment/file%d.txt";

        eventStream()
//            .window(5)
            .window(Duration.ofMillis(1800))
            .flatMap(flux -> FileWriter.create(flux, Path.of(fileNameFormat.formatted(counter.incrementAndGet()))))
            .subscribe();

        RunUtilities.sleep(60);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
//            .take(10)
            .map(i -> "item-" + i);
    }
}
