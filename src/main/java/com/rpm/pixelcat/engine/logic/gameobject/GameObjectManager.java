package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;

import java.util.ArrayList;
import java.util.List;

public interface GameObjectManager {
    public void addGameObject(GameObject gameObject) throws GameException;

    public void addGameObjects(List<GameObject> gameObjects) throws GameException;

    public int getGameObjectsCount();

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects();

    public LayerManager getLayerManager();

    public void process(KernelState kernelState) throws GameException;
}
