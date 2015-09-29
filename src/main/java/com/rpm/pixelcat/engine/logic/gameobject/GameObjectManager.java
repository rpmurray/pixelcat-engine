package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesStorageEnum;

import java.util.List;
import java.util.Set;

public interface GameObjectManager {
    GameObject createGameObject() throws GameException;

    void add(GameObject gameObject) throws GameException;

    void add(Set<GameObject> gameObjects) throws GameException;

    Boolean has(String id);

    GameObject get(String id) throws GameException;

    Integer count();

    List<List<GameObject>> getLayeredGameObjects() throws GameException;

    LayerManager getLayerManager();

    void process(KernelState kernelState) throws GameException;

    static GameObjectManager create(Integer layers) throws GameException {
        GameObjectManagerImpl gameObjectManager = new GameObjectManagerImpl(layers, PropertiesStorageEnum.HASH_MAP);

        return gameObjectManager;
    }

    static GameObjectManager create(Integer layers, PropertiesStorageEnum propertiesStorageEnum) throws GameException {
        GameObjectManagerImpl gameObjectManager = new GameObjectManagerImpl(layers, propertiesStorageEnum);

        return gameObjectManager;
    }
}
