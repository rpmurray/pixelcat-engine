package info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao;

public class PropertiesDBFactory {
    private static PropertiesDBFactory instance = null;

    public static PropertiesDBFactory getInstance() {
        if (instance == null) {
            instance = new PropertiesDBFactory();
        }

        return instance;
    }

    public PropertiesDB createHashMapPropertiesDB() {
        HashMapPropertiesDB hashMapPropertiesDB = new HashMapPropertiesDB();

        return hashMapPropertiesDB;
    }
}
