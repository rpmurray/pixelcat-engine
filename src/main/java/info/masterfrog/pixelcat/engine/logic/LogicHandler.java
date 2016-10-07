package info.masterfrog.pixelcat.engine.logic;

import info.masterfrog.pixelcat.engine.exception.ExitException;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObject;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

import java.util.List;
import java.util.Set;

public interface LogicHandler {
    List<GameObject> getGameObjects(KernelState kernelState) throws TransientGameException;

    List<List<GameObject>> getLayeredGameObjects(KernelState kernelState) throws TransientGameException;

    Set<SoundEngine.SoundEventActor> getSoundEvents(KernelState kernelState) throws TransientGameException;

    void process(KernelState kernelState) throws TransientGameException, TerminalGameException, ExitException;
}
