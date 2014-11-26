package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.constants.GameObjectKey;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.logic.GameObjectMover;

public class GameObjectManagerImpl implements GameObjectManager {
    private GameObject[] gameObjects;

    public GameObjectManagerImpl(KernelState kernelState) {
        // setup
        LayerManager layerManager = LayerManager.getInstance();

        // define layers
        layerManager.addLayers(2);

        // init game objects
        gameObjects = new GameObject[GameObjectKey.NUM_OBJS.getValue()];

        // register game objects
        registerGameObjects(kernelState);
    }

    private void registerGameObjects(KernelState kernelState) {
        // character
        registerGameObject(new com.rpm.pixelcat.logic.gameobject.Character(kernelState.getBounds()), GameObjectKey.LAYER1, GameObjectKey.CHARACTER);

        // title
        registerGameObject(new Title(kernelState.getBounds()), GameObjectKey.LAYER2, GameObjectKey.TITLE);

        // subtitle
        registerGameObject(new Subtitle(kernelState.getBounds()), GameObjectKey.LAYER2, GameObjectKey.SUBTITLE);
    }

    private void registerGameObject(GameObject gameObject, GameObjectKey layer, GameObjectKey key) {
        gameObject.setLayer(layer.getValue());
        gameObjects[key.getValue()] = gameObject;
    }

    public int getCount() {
        // setup
        Integer count = 0;

        // count objects
        for (GameObject item: gameObjects) {
            if (item != null) {
                count++;
            }
        }

        return count;
    }

    public GameObject[][] getLayeredGameObjects() {
        // setup
        GameObject[][] layeredGameObjects = new GameObject[LayerManager.getInstance().getLayerCount()][getCount()];
        Integer[] layerIndices = new Integer[LayerManager.getInstance().getLayerCount()];
        for (Integer i = 0; i < layerIndices.length; i++) {
            layerIndices[i] = 0;
        }

        // build layered game objects
        for (GameObject gameObject: gameObjects) {
            layeredGameObjects[gameObject.getLayer()][layerIndices[gameObject.getLayer()]++] = gameObject;
        }

        return layeredGameObjects;
    }

    public void process(KernelState kernelState) throws GameException {
        for (GameObject gameObject : gameObjects) {
            GameObjectMover.getInstace().move(gameObject, kernelState, gameObject.getBoundHIDEvents());
        }
    }
}
