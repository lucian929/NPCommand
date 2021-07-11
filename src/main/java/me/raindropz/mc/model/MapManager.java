package me.raindropz.mc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class MapManager<K, V> implements Manager {

    private final Map<K, V> map;

    public MapManager() {
        this.map = new HashMap<>();
    }

    public MapManager(Map<K, V> map) {
        this.map = map;
    }

    protected Map<K, V> getMap() {
        return map;
    }

    public void put(K k, V v) {
        this.map.put(k, v);
    }

    public V get(K k) {
        return this.map.get(k);
    }

    public V get(Predicate<? super Map.Entry<K, V>> predicate, V def) {
        return this.map.entrySet().stream()
                .filter(predicate)
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(def);
    }
}
