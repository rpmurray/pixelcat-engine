package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesDB;

public interface GameObjectProperties {
    void value(String key, Object value);

    Object value(String key);

    static GameObjectProperties create(PropertiesDB propertiesDB) {
        GameObjectProperties gameObjectProperties = new GameObjectPropertiesImpl(propertiesDB);

        return gameObjectProperties;
    }
}
