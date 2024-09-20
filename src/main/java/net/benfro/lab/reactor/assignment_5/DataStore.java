package net.benfro.lab.reactor.assignment_5;

import com.google.common.collect.Maps;

import java.util.Map;

public class DataStore<String,Integer> {

    Map<String,Integer> data = Maps.newHashMap();

    Integer get( String key) {
        return data.get(key);
    }

    void update(String key, Integer value) {
        data.put(key, value);
    }

    void updateAdd(String key, Integer value) {
        data.compute(key, (k, v) -> Integer.valueOf(value) + Integer.valueOf(v));
    }

}
