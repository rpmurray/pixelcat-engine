package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.logic.resource.model.ResourceImpl;

public interface GameObjectManager {
    public int getCount();

    public ResourceImpl[] getObjects();

    public void process(KernelState kernelState) throws GameException;
}
