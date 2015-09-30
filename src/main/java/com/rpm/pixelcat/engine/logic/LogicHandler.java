package com.rpm.pixelcat.engine.logic;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;

import java.util.List;

public interface LogicHandler {
    public List<List<GameObject>> getLayeredGameObjects(KernelState kernelState) throws GameException;

    public void process(KernelState kernelState) throws GameException;
}
