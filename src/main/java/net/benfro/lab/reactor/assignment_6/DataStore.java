package net.benfro.lab.reactor.assignment_6;

import java.util.Map;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class DataStore<String, V extends Number> {

    protected final Map<String, Integer> dataMap = Maps.newConcurrentMap();

    public Integer get(String key) {
        return dataMap.get(key);
    }

    public void update(String key, Integer value) {
        dataMap.put(key, value);
    }

    public void updateAdd(String key, Integer value) {
        if (dataMap.containsKey(key)) {
            dataMap.compute(key, (k, v) -> Integer.valueOf(value) + Integer.valueOf(v));
        } else {
            update(key, value);
        }

    }

    protected void logState() {
        log.info("Data: {}", dataMap);
    }

    public String report(StringBuilder sb) {
        dataMap.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + " SEK").forEach(s -> sb.append(s).append("\n"));
        return (String) sb.toString();
    }

}
