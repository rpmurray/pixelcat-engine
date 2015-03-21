package com.rpm.pixelcat.engine.logic;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManager;

import java.util.ArrayList;

public interface LogicHandler {
    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects(KernelState kernelState) throws GameException;

    public void process(KernelState kernelState) throws GameException;
}
