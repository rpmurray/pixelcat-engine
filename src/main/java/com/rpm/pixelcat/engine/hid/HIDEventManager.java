package com.rpm.pixelcat.engine.hid;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.kernel.KernelState;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface HIDEventManager {
    public void handleKeyboardEvent(HIDEventTypeEnum hidEventType, KeyEvent keyEvent) throws GameException;

    public void handleMouseEvent(HIDEventTypeEnum hidEventType, MouseEvent mouseEvent) throws GameException;

    public void generateSynthesizedEvents();

    public static HIDEventManager create(KernelState kernelState) {
        HIDEventManager hidEventManager = new HIDEventManagerImpl(kernelState);

        return hidEventManager;
    }
}
