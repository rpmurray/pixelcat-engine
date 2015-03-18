package com.rpm.pixelcat.engine.hid;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.InternalUnexpectedLogicException;
import com.rpm.pixelcat.engine.kernel.KernelState;

import java.awt.event.KeyEvent;

public class HIDEventManager {
    private final KernelState kernelState;
    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(HIDEventManager.class);

    public HIDEventManager(KernelState kernelState) {
        this.kernelState = kernelState;
    }

    public void handleKeyboardEvent(HIDKeyboardEventTypeEnum keyboardEventType, KeyEvent keyEvent) throws GameException {
        // map HID event
        try {
            HIDEventEnum hidEvent = mapKeyboardEvent(keyEvent.getKeyCode());

            // determine kernelState impact
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
        } catch (GameException e) {
            PRINTER.printInfo(e.toString() + " [" + keyEvent + "," + keyboardEventType + "]");
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
            case KeyEvent.VK_1:
                hidEvent = HIDEventEnum.SET_LOG_LVL_FATAL;
                break;
            case KeyEvent.VK_2:
                hidEvent = HIDEventEnum.SET_LOG_LVL_ERROR;
                break;
            case KeyEvent.VK_3:
                hidEvent = HIDEventEnum.SET_LOG_LVL_WARN;
                break;
            case KeyEvent.VK_4:
                hidEvent = HIDEventEnum.SET_LOG_LVL_INFO;
                break;
            case KeyEvent.VK_5:
                hidEvent = HIDEventEnum.SET_LOG_LVL_DEBUG;
                break;
            case KeyEvent.VK_6:
                hidEvent = HIDEventEnum.SET_LOG_LVL_TRACE;
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
