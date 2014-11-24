package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.constants.GameObjectKey;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.kernel.KernelState;

public class GameObjectManagerImpl implements GameObjectManager {
    private GameObject[][] gameObjects;

    public GameObjectManagerImpl(KernelState kernelState) {
        // setup
        LayerManager layerManager = LayerManager.getInstance();

        // define layers
        layerManager.addLayers(2);

        // init game objects
        gameObjects = new GameObject[layerManager.getLayerCount()][GameObjectKey.NUM_OBJS.getValue()];

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
        gameObjects[layer.getValue()][key.getValue()] = gameObject;
    }

    public int getCount() {
        Integer count = 0;
        for (GameObject[] list: gameObjects) {
            for (GameObject item: list) {
                if (item != null) {
                    count++;
                }
            }
        }

        return count;
    }

    public GameObject[][] getLayeredGameObjects() {
        return gameObjects;
    }

    public void process(KernelState kernelState) throws GameException {
        // TODO: Fix Logic Hook
        //for (GameObject object : gameObjects) {
        //    object.process(kernelState);
        //}
    }
}
