package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesStorageEnum;

import java.util.ArrayList;
import java.util.List;

public interface GameObjectManager {
    public GameObjectFactory getGameObjectFactory();

    public void addGameObject(GameObject gameObject) throws GameException;

    public void addGameObjects(List<GameObject> gameObjects) throws GameException;

    public int getGameObjectsCount();

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects();

    public LayerManager getLayerManager();

    public void process(KernelState kernelState) throws GameException;

    public static GameObjectManager create(Integer layers) throws GameException {
        GameObjectManagerImpl gameObjectManager = new GameObjectManagerImpl(layers, PropertiesStorageEnum.HASH_MAP);

        return gameObjectManager;
    }

    public static GameObjectManager create(Integer layers, PropertiesStorageEnum propertiesStorageEnum) throws GameException {
        GameObjectManagerImpl gameObjectManager = new GameObjectManagerImpl(layers, propertiesStorageEnum);

        return gameObjectManager;
    }
}
