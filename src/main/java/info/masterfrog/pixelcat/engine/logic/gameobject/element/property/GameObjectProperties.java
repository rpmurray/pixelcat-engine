package info.masterfrog.pixelcat.engine.logic.gameobject.element.property;

import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao.PropertiesDB;

public interface GameObjectProperties {
    void value(String key, Object value);

    Object value(String key);

    static GameObjectProperties create(PropertiesDB propertiesDB) {
        GameObjectProperties gameObjectProperties = new GameObjectPropertiesImpl(propertiesDB);

        return gameObjectProperties;
    }
}
