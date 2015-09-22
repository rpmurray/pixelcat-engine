package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventManager;
import com.rpm.pixelcat.engine.hid.HIDKeyboardEventTypeEnum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KernelKeyListener implements KeyListener {
    private KernelState kernelState;
    private HIDEventManager hidEventManager;

    public KernelKeyListener(KernelState kernelState, HIDEventManager hidEventManager) {
        // handle parent
        super();

        // handle local init
        this.kernelState = kernelState;
        this.hidEventManager = hidEventManager;
    }

    // Now the instance methods:
    public void keyPressed(KeyEvent key) {
        try {
            hidEventManager.handleKeyboardEvent(HIDKeyboardEventTypeEnum.KEY_PRESS, key);
        } catch (GameException e) {
            kernelState.addError(e);
        }
    }

    public void keyReleased(KeyEvent key) {
        try {
            hidEventManager.handleKeyboardEvent(HIDKeyboardEventTypeEnum.KEY_RELEASE, key);
        } catch (GameException e) {
            kernelState.addError(e);
        }
    }

    public void keyTyped(KeyEvent key) {
    }
}
