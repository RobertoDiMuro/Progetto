package it.uniroma2.dicii.ezgym.utils;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDb {

    private static InMemoryDb instance;
    private final Map<Class<?>, Map<Object, Object>> demoMemory = new HashMap<>();

    private InMemoryDb() {
        //
    }

    public synchronized static InMemoryDb getInstance() {
        if (instance == null) {
            instance = new InMemoryDb();
        }
        return instance;
    }

    public <K, V> Map<K, V> getTable(Class<V> entityClass) {
        return (Map<K, V>) demoMemory.computeIfAbsent(entityClass, c -> new HashMap<>());
    }

    
    public void clear() {
        demoMemory.clear();
    }
}
