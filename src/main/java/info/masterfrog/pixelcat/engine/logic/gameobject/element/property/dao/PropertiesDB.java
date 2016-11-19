package info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao;

public interface PropertiesDB {
    void set(String key, Object value);

    Object get(String key);

    Boolean containsKey(String key);

    Boolean containsValue(Object value);
}
