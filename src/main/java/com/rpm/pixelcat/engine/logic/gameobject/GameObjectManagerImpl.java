package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameObjectManagerImpl implements GameObjectManager {
    private List<GameObject> gameObjects;
    private LayerManager layerManager;

    public GameObjectManagerImpl(Integer layers) throws GameException {
        // init game objects
        gameObjects = new ArrayList<>();

        // layer setup
        layerManager = new LayerManagerImpl(layers);
    }

    public void addGameObject(GameObject gameObject) throws GameException {
        if (!layerManager.isValidLayer(gameObject.getLayer())) {
            throw new GameException(GameErrorCode.LOGIC_ERROR, gameObject);
        }

        gameObjects.add(gameObject);
    }

    public void addGameObject(Integer index, GameObject gameObject) throws GameException {
        if (!layerManager.isValidLayer(gameObject.getLayer())) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        gameObjects.add(index, gameObject);
    }

    public void addGameObjects(List<GameObject> gameObjects) throws GameException {
        for (GameObject gameObject: gameObjects) {
            // validate
            if (!layerManager.isValidLayer(gameObject.getLayer())) {
                throw new GameException(GameErrorCode.LOGIC_ERROR);
            }

            // add
            this.gameObjects.add(gameObject);
        }
    }

    public void addGameObjects(Map<Integer, GameObject> gameObjects) throws GameException {
        for (Integer index: gameObjects.keySet()) {
            // setup
            GameObject gameObject = gameObjects.get(index);

            // validate
            if (!layerManager.isValidLayer(gameObject.getLayer())) {
                throw new GameException(GameErrorCode.LOGIC_ERROR);
            }

            // add
            this.gameObjects.add(index, gameObject);
        }
    }

    public int getGameObjectsCount() {
        return gameObjects.size();
    }

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects() {
        // setup
        ArrayList<ArrayList<GameObject>> layeredGameObjects = new ArrayList<>();
        for (Integer i = 0; i < layerManager.getLayerCount(); i++) {
            layeredGameObjects.add(i, new ArrayList<>());
        }

        // build layered game objects
        for (GameObject gameObject: gameObjects) {
            layeredGameObjects.get(gameObject.getLayer()).add(gameObject);
        }

        return layeredGameObjects;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void process(KernelState kernelState) throws GameException {
        for (GameObject gameObject : gameObjects) {
            GameObjectUpdater.getInstace().update(gameObject, kernelState);
        }
    }
}
