package info.masterfrog.pixelcat.engine.logic;

import info.masterfrog.pixelcat.engine.exception.ExitException;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

import java.util.List;
import java.util.Set;

public interface LogicHandler {
    List<GameObject> getGameObjects() throws TransientGameException;

    List<List<GameObject>> getLayeredGameObjects() throws TransientGameException;

    Set<SoundEngine.SoundEventActor> getSoundEvents() throws TransientGameException;

    void process() throws TransientGameException, TerminalGameException, ExitException;

    static LogicHandler getInstance() {
        return LogicHandlerImpl.getInstance();
    }
}
