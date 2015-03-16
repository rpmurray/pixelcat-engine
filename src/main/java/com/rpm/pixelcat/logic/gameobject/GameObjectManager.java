package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.kernel.KernelState;

import java.util.ArrayList;
import java.util.List;

public interface GameObjectManager {
    public int getCount();

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects();

    public void process(KernelState kernelState) throws GameException;
}
