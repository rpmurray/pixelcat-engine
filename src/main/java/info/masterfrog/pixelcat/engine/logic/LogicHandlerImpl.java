package info.masterfrog.pixelcat.engine.logic;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.util.MapBuilder;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelActionEnum;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObjectManager;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.exception.ExitException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStatePropertyEnum;
import info.masterfrog.pixelcat.engine.logic.gameobject.feature.SoundLibrary;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;
import org.apache.log4j.Level;

import java.util.*;

public class LogicHandlerImpl implements LogicHandler {
    public List<GameObject> getGameObjects(KernelState kernelState) throws TransientGameException {
        // setup
        List<GameObject> consolidatedGameObjects = new ArrayList<>();

        // retrieve game object managers
        List<GameObjectManager> gameObjectManagers = (List) kernelState.getProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS);

        // loop through each and consolidate
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            consolidatedGameObjects.addAll(gameObjectManager.getGameObjects().values());
        }

        return consolidatedGameObjects;
    }

    public List<List<GameObject>> getLayeredGameObjects(KernelState kernelState) throws TransientGameException {
        // setup
        List<List<GameObject>> consolidatedLayeredGameObjects = new ArrayList<>();

        // retrieve game object managers
        List<GameObjectManager> gameObjectManagers = (List) kernelState.getProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS);

        // loop through each and consolidate
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            // setup
            List<List<GameObject>> layeredGameObjects = gameObjectManager.getLayeredGameObjects();

            // iterate through the layers
            Integer i = 0;
            for (List<GameObject> gameObjects: layeredGameObjects) {
                // save game objects in appropriate layer
                if (consolidatedLayeredGameObjects.size() <= i || consolidatedLayeredGameObjects.get(i) == null) {
                    consolidatedLayeredGameObjects.add(i, gameObjects);
                } else {
                    consolidatedLayeredGameObjects.get(i).addAll(gameObjects);
                }

                // increment iterator
                i++;
            }
        }

        return consolidatedLayeredGameObjects;
    }

    public Set<SoundEngine.SoundEventActor> getSoundEvents(KernelState kernelState) throws TransientGameException {
        Set<SoundEngine.SoundEventActor> soundEvents = new HashSet<>();

        // get all game objects
        List<GameObject> gameObjects = getGameObjects(kernelState);

        // iterate through and find sound event capable objects
        for (GameObject gameObject : gameObjects) {
            if (gameObject.hasFeature(SoundLibrary.class)) {
                // get sound library
                SoundLibrary soundLibrary = gameObject.getFeature(SoundLibrary.class);

                // iterate through all sound library elements
                for (String soundResourceId : soundLibrary.getAll().keySet()) {
                    // check each sound event actor for existence of actionable sound event states
                    // and add such sound event actors to our set of sound events
                    SoundEngine.SoundEventActor soundEventActor = soundLibrary.get(soundResourceId);
                    if (soundEventActor.containsSoundEventStates()) {
                        soundEvents.add(soundEventActor);
                    }
                }
            }
        }

        return soundEvents;
    }

    public void process(KernelState kernelState) throws TransientGameException, TerminalGameException, ExitException {
        // do logic checks
        checkExit(kernelState);

        // do gameObjectManager handling
        List<GameObjectManager> gameObjectManagers = (List<GameObjectManager>) kernelState.getProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS);
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            gameObjectManager.process(kernelState);
        }

        // update game state
        updateGameState(kernelState);
    }

    private void checkExit(KernelState kernelState) throws ExitException {
        if (kernelState.hasKernelAction(KernelActionEnum.EXIT)) {
            // trigger an exit
            throw new ExitException();
        }
    }

    private void updateGameState(KernelState kernelState) throws TransientGameException {
        // handle logging
        handleLogging(kernelState);

        // handle font display
        handleFontDisplay(kernelState);
    }

    private void handleLogging(KernelState kernelState) throws TransientGameException {
        // logging level
        setKernelStateProperty(
            kernelState,
            KernelStatePropertyEnum.LOG_LVL,
            new MapBuilder<HashMap, KernelActionEnum, Level>(HashMap.class).add(
                KernelActionEnum.SET_LOG_LVL_FATAL, Printer.getLogLevelFatal()
            ).add(
                KernelActionEnum.SET_LOG_LVL_ERROR, Printer.getLogLevelError()
            ).add(
                KernelActionEnum.SET_LOG_LVL_WARN, Printer.getLogLevelWarn()
            ).add(
                KernelActionEnum.SET_LOG_LVL_INFO, Printer.getLogLevelInfo()
            ).add(
                KernelActionEnum.SET_LOG_LVL_DEBUG, Printer.getLogLevelDebug()
            ).add(
                KernelActionEnum.SET_LOG_LVL_TRACE, Printer.getLogLevelTrace()
            ).get()
        );
    }

    private void handleFontDisplay(KernelState kernelState) throws TransientGameException {
        // font debugger
        toggleKernelStateProperty(kernelState, KernelActionEnum.FONT_DEBUG_TOGGLE, KernelStatePropertyEnum.FONT_DISPLAY_ENABLED);
    }

    private void setKernelStateProperty(KernelState kernelState,
                                        KernelStatePropertyEnum kernelStateProperty,
                                        Map<KernelActionEnum, Object> kernelActionBindings)
                 throws TransientGameException {
        for (KernelActionEnum kernelAction: kernelActionBindings.keySet()) {
            // setup
            Object kernelStatePropertyValue = kernelActionBindings.get(kernelAction);

            // check hid event and set kernel state property
            if (kernelState.hasKernelAction(kernelAction)) {
                // remove kernel action so it isn't processed twice
                kernelState.removeKernelAction(kernelAction);

                // set kernel state property
                kernelState.setProperty(kernelStateProperty, kernelStatePropertyValue);
            }
        }
    }

    private void toggleKernelStateProperty(KernelState kernelState,
                                           KernelActionEnum kernelAction,
                                           KernelStatePropertyEnum kernelStateProperty)
                 throws TransientGameException {
        if (kernelState.hasKernelAction(kernelAction)) {
            // remove kernel action so it isn't processed twice
            kernelState.removeKernelAction(kernelAction);

            // toggle kernel state property
            if (kernelState.getPropertyFlag(kernelStateProperty)) {
                kernelState.setProperty(kernelStateProperty, false);
            } else {
                kernelState.setProperty(kernelStateProperty, true);
            }
        }
    }
}
