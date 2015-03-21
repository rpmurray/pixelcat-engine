package com.rpm.pixelcat.engine.logic;

import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManager;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.exception.ExitException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogicHandlerImpl implements LogicHandler {
    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects(KernelState kernelState) throws GameException {
        // setup
        ArrayList<ArrayList<GameObject>> consolidatedLayeredGameObjects = new ArrayList<>();

        // retrieve game object managers
        List<GameObjectManager> gameObjectManagers = (List) kernelState.getProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS);

        // loop through each and consolidate
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            // setup
            ArrayList<ArrayList<GameObject>> layeredGameObjects = gameObjectManager.getLayeredGameObjects();

            // iterate through the layers
            Integer i = 0;
            for (ArrayList<GameObject> gameObjects: layeredGameObjects) {
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

    public void process(KernelState kernelState) throws GameException {
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
        if (kernelState.getPropertyFlag(KernelStatePropertyEnum.EXIT_SIGNAL)) {
            // remove event so it isn't processed again
            kernelState.removeHIDEvent(HIDEventEnum.BACK);

            // trigger an exit
            throw new ExitException();
        }
    }

    private void updateGameState(KernelState kernelState) throws GameException {
        // handle logging
        handleLogging(kernelState);

        // handle font display
        handleFontDisplay(kernelState);
    }

    private void handleLogging(KernelState kernelState) throws GameException {
        // logging level
        setKernelStateProperty(
            kernelState,
            KernelStatePropertyEnum.LOG_LVL,
            ImmutableMap.<HIDEventEnum, Object>builder().put(
                HIDEventEnum.SET_LOG_LVL_FATAL, Level.FATAL
            ).put(
                HIDEventEnum.SET_LOG_LVL_ERROR, Level.ERROR
            ).put(
                HIDEventEnum.SET_LOG_LVL_WARN, Level.WARN
            ).put(
                HIDEventEnum.SET_LOG_LVL_INFO, Level.INFO
            ).put(
                HIDEventEnum.SET_LOG_LVL_DEBUG, Level.DEBUG
            ).put(
                HIDEventEnum.SET_LOG_LVL_TRACE, Level.TRACE
            ).build()
        );
    }

    private void handleFontDisplay(KernelState kernelState) throws GameException {
        // font debugger
        toggleKernelStateProperty(kernelState, HIDEventEnum.FONT_DEBUG_TOGGLE, KernelStatePropertyEnum.FONT_DISPLAY_ENABLED);
    }

    private void setKernelStateProperty(KernelState kernelState,
                                        KernelStatePropertyEnum kernelStateProperty,
                                        Map<HIDEventEnum, Object> hidEventKernelStatePropertyMap)
                 throws GameException {
        for (HIDEventEnum hidEvent: hidEventKernelStatePropertyMap.keySet()) {
            // setup
            Object kernelStatePropertyValue = hidEventKernelStatePropertyMap.get(hidEvent);

            // check hid event and set kernel state property
            if (kernelState.hasHIDEvent(hidEvent)) {
                // remove HID event so it isn't processed twice
                kernelState.removeHIDEvent(hidEvent);

                // set kernel state property
                kernelState.setProperty(kernelStateProperty, kernelStatePropertyValue);
            }
        }
    }

    private void toggleKernelStateProperty(KernelState kernelState,
                                           HIDEventEnum hidEvent,
                                           KernelStatePropertyEnum kernelStateProperty)
                 throws GameException {
        if (kernelState.hasHIDEvent(hidEvent)) {
            // remove HID event so it isn't processed twice
            kernelState.removeHIDEvent(hidEvent);

            // toggle kernel state property
            if (kernelState.getPropertyFlag(kernelStateProperty)) {
                kernelState.setProperty(kernelStateProperty, false);
            } else {
                kernelState.setProperty(kernelStateProperty, true);
            }
        }
    }
}
