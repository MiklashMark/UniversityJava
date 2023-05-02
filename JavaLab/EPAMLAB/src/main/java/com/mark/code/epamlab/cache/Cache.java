package com.mark.code.epamlab.cache;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Cache<K, V> {
    private Map<K, V> cache = new HashMap<>();

    public boolean isContains(K key) {
        return cache.containsKey(key);
    }

    public void push(K key, V value) {
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void a(){
        List<Integer> list = new ArrayList<>();

    }

}
