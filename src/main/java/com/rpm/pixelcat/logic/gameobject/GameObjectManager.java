package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.kernel.KernelState;

public interface GameObjectManager {
    public int getCount();

    public GameObject[][] getLayeredGameObjects();

    public void process(KernelState kernelState) throws GameException;
}
