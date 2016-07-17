package info.masterfrog.pixelcat.engine.logic;

import com.google.common.collect.ImmutableMap;
import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelActionEnum;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObjectManager;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.exception.ExitException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStatePropertyEnum;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicHandlerImpl implements LogicHandler {
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

    public Map<SoundEngine.SoundResourceState, SoundResource> getSoundEvents() {
        Map<SoundEngine.SoundResourceState, SoundResource> soundResourceMap = new HashMap<>();

        return soundResourceMap;
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
            ImmutableMap.<KernelActionEnum, Object>builder().put(
                KernelActionEnum.SET_LOG_LVL_FATAL, Printer.getLogLevelFatal()
            ).put(
                KernelActionEnum.SET_LOG_LVL_ERROR, Printer.getLogLevelError()
            ).put(
                KernelActionEnum.SET_LOG_LVL_WARN, Printer.getLogLevelWarn()
            ).put(
                KernelActionEnum.SET_LOG_LVL_INFO, Printer.getLogLevelInfo()
            ).put(
                KernelActionEnum.SET_LOG_LVL_DEBUG, Printer.getLogLevelDebug()
            ).put(
                KernelActionEnum.SET_LOG_LVL_TRACE, Printer.getLogLevelTrace()
            ).build()
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
