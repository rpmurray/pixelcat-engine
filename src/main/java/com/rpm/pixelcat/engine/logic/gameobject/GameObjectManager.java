package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;

import java.util.ArrayList;

public interface GameObjectManager {
    public int getCount();

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects();

    public void process(KernelState kernelState) throws GameException;
}
