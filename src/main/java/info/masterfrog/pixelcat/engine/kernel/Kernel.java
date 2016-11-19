package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.RecoverableTerminalErrorException;
import info.masterfrog.pixelcat.engine.exception.TerminalErrorException;
import info.masterfrog.pixelcat.engine.logic.gameobject.manager.GameObjectManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Kernel {
    void init(HashMap<KernelStateProperty, Object> kernelStateInitProperties) throws TerminalErrorException;

    void registerGameObjectManagers(List<GameObjectManager> gameObjectManagers) throws RecoverableTerminalErrorException;

    void kernelMain(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap);
}
