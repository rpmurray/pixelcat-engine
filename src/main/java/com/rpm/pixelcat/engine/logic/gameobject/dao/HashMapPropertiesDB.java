package com.rpm.pixelcat.engine.logic.gameobject.dao;

import java.util.HashMap;

public class HashMapPropertiesDB implements PropertiesDB {
    private HashMap<String, Object> hashMap;

    public HashMapPropertiesDB() {
        hashMap = new HashMap<>();
    }

    public void set(String key, Object value) {
        hashMap.put(key, value);
    }

    public Object get(String key) {
        return hashMap.get(key);
    }

    public Boolean containsKey(String key) {
        return hashMap.containsKey(key);
    }

    public Boolean containsValue(Object value) {
        return hashMap.containsValue(value);
    }
}
