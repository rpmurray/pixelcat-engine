package info.masterfrog.pixelcat.engine.logic.gameobject;

import info.masterfrog.pixelcat.engine.logic.gameobject.dao.PropertiesDB;

public interface GameObjectProperties {
    void value(String key, Object value);

    Object value(String key);

    static GameObjectProperties create(PropertiesDB propertiesDB) {
        GameObjectProperties gameObjectProperties = new GameObjectPropertiesImpl(propertiesDB);

        return gameObjectProperties;
    }
}
