package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.gameobject.dao.HashMapPropertiesDB;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesDB;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesStorageEnum;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Renderable;

import java.util.*;

public class GameObjectManagerImpl implements GameObjectManager {
    private PropertiesDB propertiesDB;
    private Map<String, GameObject> gameObjects;
    private LayerManager layerManager;

    public GameObjectManagerImpl(Integer layers, PropertiesStorageEnum propertiesStorageEnum) throws GameException {
        // init game objects
        gameObjects = new HashMap<>();

        // layer setup
        layerManager = new LayerManagerImpl(layers);

        // game object factory setup
        switch (propertiesStorageEnum) {
            case XML:
                throw new GameException(GameErrorCode.INTERNAL_ERROR, "Unsupported game object properties DB");
            case HASH_MAP:
            default:
                propertiesDB = new HashMapPropertiesDB();
                break;
        }
    }

    public GameObject createGameObject() throws GameException {
        GameObject gameObject = GameObject.create(GameObjectProperties.create(propertiesDB));

        return gameObject;
    }

    public void add(GameObject gameObject) throws GameException {
        if (gameObject.hasFeature(Renderable.class)) {
            if (!layerManager.isValidLayer(gameObject.getFeature(Renderable.class).getLayer())) {
                throw new GameException(GameErrorCode.LOGIC_ERROR, gameObject);
            }
        }

        gameObjects.put(gameObject.getId(), gameObject);
    }

    public void add(Set<GameObject> gameObjects) throws GameException {
        // add one at a time
        for (GameObject gameObject : gameObjects) {
            add(gameObject);
        }
    }

    public Boolean has(String id) {
        return gameObjects.containsKey(id);
    }

    public GameObject get(String id) throws GameException {
        if (!has(id)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return gameObjects.get(id);
    }

    public Integer count() {
        return gameObjects.size();
    }

    public List<List<GameObject>> getLayeredGameObjects() throws GameException {
        // setup
        List<List<GameObject>> layeredGameObjects = new ArrayList<>();
        for (Integer i = 0; i < layerManager.getLayerCount(); i++) {
            layeredGameObjects.add(i, new ArrayList<>());
        }

        // build layered game objects
        for (String id : gameObjects.keySet()) {
            // setup
            Integer layerIndex = 0;
            GameObject gameObject = gameObjects.get(id);

            // set layer index if applicable
            if (gameObject.hasFeature(Renderable.class)) {
                layerIndex = gameObject.getFeature(Renderable.class).getLayer();
            }

            // add game object to layered set of game objects
            layeredGameObjects.get(layerIndex).add(gameObject);
        }

        return layeredGameObjects;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void process(KernelState kernelState) throws GameException {
        for (String id : gameObjects.keySet()) {
            // setup
            GameObject gameObject = gameObjects.get(id);

            // update
            GameObjectUpdater.getInstace().update(gameObject, kernelState);
        }
    }
}
