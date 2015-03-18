package com.rpm.pixelcat.engine.logic;

import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManagerImpl;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.exception.ExitException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;
import org.apache.log4j.Level;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

public class LogicHandler {
    GameObjectManagerImpl gameObjectManager;

    public LogicHandler(KernelState state) throws IOException, URISyntaxException, GameException {
        gameObjectManager = new GameObjectManagerImpl(state);
    }

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects() {
        return gameObjectManager.getLayeredGameObjects();
    }

    public void process(KernelState kernelState) throws GameException {
        // do logic checks
        checkExit(kernelState);

        // do gameObjectManager handling
        gameObjectManager.process(kernelState);

        // update game state
        updateGameState(kernelState);
    }

    private void checkExit(KernelState kernelState) throws ExitException {
        if (kernelState.hasHIDEvent(HIDEventEnum.EXIT)) {
            // remove event so it isn't processed again
            kernelState.removeHIDEvent(HIDEventEnum.EXIT);

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
