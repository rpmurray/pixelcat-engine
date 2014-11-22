package com.rpm.pixelcat.logic;

import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.exception.ExitException;
import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.logic.resource.model.Resource;
import com.rpm.pixelcat.logic.gameobject.GameObjectManagerImpl;

import java.io.IOException;
import java.net.URISyntaxException;

public class LogicHandler {
    GameObjectManagerImpl resourceManager;

    public LogicHandler(KernelState state) throws IOException, URISyntaxException {
        resourceManager = new GameObjectManagerImpl(state);
    }

    public Resource[] getObjects() {
        return resourceManager.getObjects();
    }

    public void process(KernelState kernelState) throws GameException {
        // do logic checks
        checkExit(kernelState);

        // do resourceManager handling
        resourceManager.process(kernelState);

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

    private void updateGameState(KernelState kernelState) {
        // handle debuggers
        handleDebuggers(kernelState);
    }

    private void handleDebuggers(KernelState kernelState) {
        // normal debug console
        toggleKernelStateProperty(kernelState, HIDEventEnum.DEBUG_TOGGLE, KernelStatePropertyEnum.DEBUG_ENABLED);

        // font debugger
        toggleKernelStateProperty(kernelState, HIDEventEnum.FONT_DEBUG_TOGGLE, KernelStatePropertyEnum.FONT_DEBUG_ENABLED);
    }

    private void toggleKernelStateProperty(KernelState kernelState, HIDEventEnum hidEvent, KernelStatePropertyEnum kernelStateProperty) {
        if (kernelState.hasHIDEvent(hidEvent)) {
            // remove HID event so it isn't processed twice
            kernelState.removeHIDEvent(hidEvent);

            // toggle kernel state property
            if (kernelState.getPropertyAsBoolean(kernelStateProperty)) {
                kernelState.setProperty(kernelStateProperty, false);
            } else {
                kernelState.setProperty(kernelStateProperty, true);
            }
        }
    }
}
