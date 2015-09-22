package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesDB;

public class GameObjectPropertiesImpl implements GameObjectProperties {
    private PropertiesDB propertiesDB;

    public GameObjectPropertiesImpl(PropertiesDB propertiesDB) {
        this.propertiesDB = propertiesDB;
    }

    public void value(String key, Object value) {
        propertiesDB.set(key, value);
    }

    public Object value(String key) {
        return propertiesDB.get(key);
    }
}
