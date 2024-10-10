package net.benfro.lab.reactor.ideas.freqtable;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    public static void main(String[] args) {

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 10; i++) {
//            FrequencyCounter r = runReactively(10000000);
        FrequencyCounter r = runImperatively(10000000);
            log.info("Max frequency: {} for {}",
                r.getMax(),
                r.getInverse(r.getMax()));
            log.info("Sum: {}", r.getSumOfValues());
        }
        log.info("Generation took {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

    }

    static FrequencyCounter runReactively(int numSamples) {
        FrequencyCounter frequencyCounter = new FrequencyCounter();
        FrequencyTable frequencyTable = new FrequencyTable(frequencyCounter);
        frequencyTable.reactiveVersion(numSamples);
        return frequencyCounter;
    }

    static FrequencyCounter runImperatively(int numSamples) {
        FrequencyCounter frequencyCounter = new FrequencyCounter();
        FrequencyTable frequencyTable = new FrequencyTable(frequencyCounter);
        frequencyTable.imperativeVersion(numSamples);
        return frequencyCounter;
    }
}
