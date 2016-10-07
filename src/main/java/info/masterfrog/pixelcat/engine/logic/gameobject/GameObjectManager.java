package info.masterfrog.pixelcat.engine.logic.gameobject;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.gameobject.dao.PropertiesStorageEnum;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GameObjectManager extends IdGenerator {
    GameObject createGameObject() throws TransientGameException;

    GameObjectManager add(GameObject gameObject) throws TransientGameException;

    GameObjectManager add(Set<GameObject> gameObjects) throws TransientGameException;

    Boolean has(String id);

    GameObject get(String id) throws TransientGameException;

    Integer count();

    Map<String, GameObject> getGameObjects();

    List<List<GameObject>> getLayeredGameObjects() throws TransientGameException;

    LayerManager getLayerManager();

    void process(KernelState kernelState) throws TransientGameException;

    static GameObjectManager create(Integer layers) throws TransientGameException {
        GameObjectManagerImpl gameObjectManager = new GameObjectManagerImpl(layers, PropertiesStorageEnum.HASH_MAP);

        return gameObjectManager;
    }

    static GameObjectManager create(Integer layers, PropertiesStorageEnum propertiesStorageEnum) throws TransientGameException {
        GameObjectManagerImpl gameObjectManager = new GameObjectManagerImpl(layers, propertiesStorageEnum);

        return gameObjectManager;
    }
}
