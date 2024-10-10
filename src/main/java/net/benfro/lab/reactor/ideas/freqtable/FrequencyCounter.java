package net.benfro.lab.reactor.ideas.freqtable;

import java.util.Map;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.Getter;

@Getter
class FrequencyCounter {

    private final Map<Integer, Long> frequencies = Maps.newHashMap();

    public void put(int key) {
        frequencies.putIfAbsent(key, 0L);
        frequencies.compute(key, (k, l) -> l + 1);
    }

    public long get(int key) {
        return frequencies.getOrDefault(key, 0L);
    }

    private HashBiMap<Integer, Long> toBiMap() {
        var map = HashBiMap.<Integer, Long>create(frequencies.size());
        for (var entry : frequencies.entrySet()) {
            map.forcePut(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public long getInverse(long valueIsKey) {
        return toBiMap().inverse().get(valueIsKey);
    }

    public long getMax() {
        return getFrequencies()
            .values()
            .stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0L);
    }

    public long getSumOfValues() {
        return getFrequencies().values().stream().mapToLong(Long::longValue).sum();
    }
}
