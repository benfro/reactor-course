package net.benfro.lab.reactor.ideas;

import java.security.SecureRandom;
import java.util.Map;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.common.RunUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

@Slf4j
public class FrequencyTable {

    private static SecureRandom random = new SecureRandom(new SecureRandom().generateSeed(20));

    @Getter
    static class FrequencyCounter {

        private final Map<Integer, Long> frequencies = Maps.newConcurrentMap();

        public void put(int key) {
            frequencies.putIfAbsent(key, 0L);
            frequencies.compute(key, (k, l) -> l + 1);
        }

        public long get(int key) {
            return frequencies.getOrDefault(key, 0L);
        }

        public HashBiMap<Integer, Long> toBiMap() {
            var map = HashBiMap.<Integer, Long>create(frequencies.size());
            for (var entry : frequencies.entrySet()) {
                map.forcePut(entry.getKey(), entry.getValue());
            }
            return map;
        }

        public long getMax() {
            return getFrequencies().values().stream().mapToLong(Long::longValue).max().orElse(0L);
        }
    }

    static Flux<Integer> publishRandoms() {
        return Flux.generate(
            FrequencyTable::getGenerator,
            FrequencyTable::generate
           );
    }

    private static SecureRandom getGenerator() {
        return random;
    }

    private static SecureRandom generate(SecureRandom generator, SynchronousSink<Integer> sink) {
        sink.next(generator.nextInt(1000) + 1);
        return generator;
    }

    public static void main(String[] args) {

        var frequencyCounter = new FrequencyCounter();

        Runnable r = () -> publishRandoms()
            .take(1000000)
            .subscribe(frequencyCounter::put);

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(r);
        }

        RunUtilities.sleep(12);
        log.info("Max frequency: {} for {}",
            frequencyCounter.getMax(),
            frequencyCounter.toBiMap().inverse().get(frequencyCounter.getMax()));
        log.info("Sum: {}", frequencyCounter.getFrequencies().values().stream().mapToLong(Long::longValue).sum());
    }

}