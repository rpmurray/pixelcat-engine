package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesStorageEnum;

import java.util.List;
import java.util.Set;

public interface GameObjectManager extends IdGenerator {
    GameObject createGameObject() throws TransientGameException;

    GameObjectManager add(GameObject gameObject) throws TransientGameException;

    GameObjectManager add(Set<GameObject> gameObjects) throws TransientGameException;

    Boolean has(String id);

    GameObject get(String id) throws TransientGameException;

    Integer count();

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
