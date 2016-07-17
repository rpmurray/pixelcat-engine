package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface HIDEventManager {
    public void handleKeyboardEvent(HIDEventTypeEnum hidEventType, KeyEvent keyEvent) throws TransientGameException;

    public void handleMouseEvent(HIDEventTypeEnum hidEventType, MouseEvent mouseEvent) throws TransientGameException;

    public void generateSynthesizedEvents();

    public static HIDEventManager create(KernelState kernelState) {
        HIDEventManager hidEventManager = new HIDEventManagerImpl(kernelState);

        return hidEventManager;
    }
}
