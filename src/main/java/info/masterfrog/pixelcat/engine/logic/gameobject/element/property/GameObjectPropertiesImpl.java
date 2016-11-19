package info.masterfrog.pixelcat.engine.logic.gameobject.element.property;

import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao.PropertiesDB;

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
