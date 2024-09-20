package net.benfro.lab.reactor.assignment_5;

import com.google.common.collect.Maps;

import java.util.Map;

public class DataStore<String, V extends Number> {

    Map<String, Integer> data = Maps.newHashMap();

    public Integer get( String key) {
        return data.get(key);
    }

    public void update(String key, Integer value) {
        data.put(key, value);
    }

    public void updateAdd(String key, Integer value) {
        data.compute(key, (k, v) -> Integer.valueOf(value) + Integer.valueOf(v));
    }

}
