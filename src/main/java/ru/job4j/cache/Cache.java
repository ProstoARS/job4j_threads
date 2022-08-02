package ru.job4j.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final static int UPDATE_VERSION = 1;
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
           if (value.getVersion() != model.getVersion()) {
               throw new OptimisticException("version base model not equals");
           }
           Base update = new Base(key, value.getVersion() + UPDATE_VERSION);
           update.setName(model.getName());
            return update;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int key) {
        return memory.get(key);
    }
}