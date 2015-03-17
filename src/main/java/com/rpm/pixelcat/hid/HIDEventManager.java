package com.rpm.pixelcat.hid;

import com.rpm.pixelcat.exception.GameErrorCode;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.exception.InternalUnexpectedLogicException;
import com.rpm.pixelcat.kernel.KernelState;

import java.awt.event.KeyEvent;

public class HIDEventManager {
    private final KernelState kernelState;

    public HIDEventManager(KernelState kernelState) {
        this.kernelState = kernelState;
    }

    public void handleKeyboardEvent(HIDKeyboardEventTypeEnum keyboardEventType, KeyEvent keyEvent) throws GameException {
        // map HID event
        HIDEventEnum hidEvent = mapKeyboardEvent(keyEvent.getKeyCode());

        // determine kernel kernelState impact
        switch (keyboardEventType) {
            case KEY_PRESS:
                kernelState.addHIDEvent(hidEvent);
                break;
            case KEY_RELEASE:
                kernelState.removeHIDEvent(hidEvent);
                break;
            default:
                throw new InternalUnexpectedLogicException(GameErrorCode.HID_KEYBOARD_EVENT_TYPE_UNSUPPORTED);
        }
    }

    private HIDEventEnum mapKeyboardEvent(Integer keyCode) throws GameException {
        // setup
        HIDEventEnum hidEvent;

        // handle key code triggered hid events
        switch (keyCode) {
            case KeyEvent.VK_UP:
                hidEvent = HIDEventEnum.UP;
                break;
            case KeyEvent.VK_DOWN:
                hidEvent = HIDEventEnum.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                hidEvent = HIDEventEnum.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                hidEvent = HIDEventEnum.RIGHT;
                break;
            case KeyEvent.VK_D:
                hidEvent = HIDEventEnum.DEBUG_TOGGLE;
                break;
            case KeyEvent.VK_F:
                hidEvent = HIDEventEnum.FONT_DEBUG_TOGGLE;
                break;
            case KeyEvent.VK_ESCAPE:
                hidEvent = HIDEventEnum.EXIT;
                break;
            default:
                throw new GameException(GameErrorCode.HID_KEYBOARD_EVENT_UNSUPPORTED);
        }

        return hidEvent;
    }

    public void generateSynthesizedEvents() {
        // no directional keyboard events
        if (!kernelState.hasHIDEvent(HIDEventEnum.UP) &&
            !kernelState.hasHIDEvent(HIDEventEnum.DOWN) &&
            !kernelState.hasHIDEvent(HIDEventEnum.LEFT) &&
            !kernelState.hasHIDEvent(HIDEventEnum.RIGHT)) {
            kernelState.addHIDEvent(HIDEventEnum.NO_DIRECTION);
        } else {
            kernelState.removeHIDEvent(HIDEventEnum.NO_DIRECTION);
        }
    }
}
