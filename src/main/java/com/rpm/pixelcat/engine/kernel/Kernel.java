package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Kernel {
    public void init(HashMap<KernelStatePropertyEnum, Object> kernelStateInitProperties) throws IOException, URISyntaxException, GameException;

    public void kernelMain(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws Exception;

    public KernelState getKernelState();

    public void registerGameObjectManagers(List<GameObjectManager> gameObjectManagers) throws GameException;
}
