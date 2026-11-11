package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!map.containsKey(key)) {
            throw new ADTException("key '" + key + "' is not defined in the dictionary");
        }
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!map.containsKey(key)) {
            throw new ADTException("cannot update undefined key: '" + key + "'");
        }
        map.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!map.containsKey(key)) {
            throw new ADTException("Key '" + key + "' not found in dictionary");
        }
        map.remove(key);
    }

    @Override
    public Map<K, V> toMap() {
        return Map.copyOf(map);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append(" --> ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return sb.toString().stripTrailing();
    }
}