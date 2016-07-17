package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TerminalErrorException;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObjectManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Kernel {
    public void init(HashMap<KernelStatePropertyEnum, Object> kernelStateInitProperties) throws TerminalErrorException;

    public void kernelMain(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap);

    public KernelState getKernelState();

    public void registerGameObjectManagers(List<GameObjectManager> gameObjectManagers);
}
