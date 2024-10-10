package net.benfro.lab.reactor.ideas.freqtable;

import java.security.SecureRandom;
import java.util.SplittableRandom;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.generator.AbstractFluxSinkGenerator;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
public class FrequencyTable {

    @Getter
    private SplittableRandom random = new SplittableRandom(System.currentTimeMillis());

    private final FrequencyCounter frequencyCounter;

    Flux<Integer> publishRandoms() {
        return Flux.generate(
            () -> getRandom(),
            (rGen, sink) -> {
                sink.next(rGen.nextInt(1, 1001));
                return rGen;
            });
    }

    public void reactiveVersion(int numSamples) {
        publishRandoms()
            .take(numSamples)
            .subscribe(frequencyCounter::put); // 2.3 sec

//        SplittableRandom rand = new SplittableRandom(System.currentTimeMillis());
//        IntStream ints = rand.ints(1000000, 1, 1001);
//        Flux.fromStream(ints.boxed()).subscribe(frequencyCounter::put); // 3.1 sec
    }

    public void imperativeVersion(int numSamples) {
        for (int i = 0; i < numSamples; i++) {
            frequencyCounter.put(random.nextInt(1, 1001));
        }
    }

}
