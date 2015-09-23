package com.rpm.pixelcat.engine.hid;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.InternalUnexpectedLogicException;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

class HIDEventManagerImpl implements HIDEventManager {
    private final KernelState kernelState;
    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(HIDEventManagerImpl.class);

    public HIDEventManagerImpl(KernelState kernelState) {
        this.kernelState = kernelState;
    }

    public void handleKeyboardEvent(HIDEventTypeEnum hidEventType, KeyEvent keyEvent) throws GameException {
        // map HID event
        try {
            HIDEventEnum hidEvent = mapKeyboardEvent(keyEvent);

            // determine kernelState impact
            switch (hidEventType) {
                case PRESS:
                    kernelState.addHIDEvent(hidEvent);
                    break;
                case RELEASE:
                    kernelState.removeHIDEvent(hidEvent);
                    break;
                default:
                    throw new InternalUnexpectedLogicException(GameErrorCode.HID_EVENT_TYPE_UNSUPPORTED);
            }
        } catch (GameException e) {
            PRINTER.printInfo(e.toString() + " [" + keyEvent + "," + hidEventType + "]");
        }
    }

    public void handleMouseEvent(HIDEventTypeEnum hidEventType, MouseEvent mouseEvent) throws GameException {
        // map HID event
        try {
            HIDEventEnum hidEvent = mapMouseEvent(mouseEvent);

            // determine kernelState impact
            switch (hidEventType) {
                case PRESS:
                    kernelState.addHIDEvent(hidEvent);
                    break;
                case RELEASE:
                    kernelState.removeHIDEvent(hidEvent);
                    break;
                case SCROLL:
                    kernelState.addHIDEvent(hidEvent);
                    break;
                case DRAG:
                default:
                    throw new InternalUnexpectedLogicException(GameErrorCode.HID_EVENT_TYPE_UNSUPPORTED);
            }
        } catch (GameException e) {
            PRINTER.printInfo(e.toString() + " [" + mouseEvent + "," + hidEventType + "]");
        }
    }

    private HIDEventEnum mapKeyboardEvent(KeyEvent keyEvent) throws GameException {
        // setup
        HIDEventEnum hidEvent;

        // fetch hid event bindings
        HIDEventBinder hidEventBinder = (HIDEventBinder) kernelState.getProperty(KernelStatePropertyEnum.HID_EVENT_BINDER);

        // derive key code from key event
        Integer keyCode = keyEvent.getKeyCode();

        // handle key code triggered hid events
        hidEvent = hidEventBinder.binding(keyCode);

        return hidEvent;
    }

    private HIDEventEnum mapMouseEvent(MouseEvent mouseEvent) throws GameException {
        // setup
        HIDEventEnum hidEvent;

        // fetch hid event bindings
        HIDEventBinder hidEventBinder = (HIDEventBinder) kernelState.getProperty(KernelStatePropertyEnum.HID_EVENT_BINDER);

        // derive button code from mouse event
        Integer buttonCode = mouseEvent.getButton();

        // handle mouse triggered hid events
        if (buttonCode.equals(MouseEvent.MOUSE_WHEEL)) {
            MouseWheelEvent mouseWheelEvent = (MouseWheelEvent) mouseEvent;
            Double magnitude = mouseWheelEvent.getPreciseWheelRotation();
            if (magnitude > 0) {
                hidEvent = HIDEventEnum.SCROLL_UP;
                hidEvent.value("magnitude", Math.abs(magnitude));
            } else {
                hidEvent = HIDEventEnum.SCROLL_DOWN;
                hidEvent.value("magnitude", Math.abs(magnitude));
            }
        } else {
            hidEvent = hidEventBinder.binding(buttonCode);
        }

        return hidEvent;
    }

    public void generateSynthesizedEvents() {
        // no directional keyboard events
        if (!kernelState.hasHIDEvent(HIDEventEnum.PRIMARY_UP) &&
            !kernelState.hasHIDEvent(HIDEventEnum.PRIMARY_DOWN) &&
            !kernelState.hasHIDEvent(HIDEventEnum.PRIMARY_LEFT) &&
            !kernelState.hasHIDEvent(HIDEventEnum.PRIMARY_RIGHT)) {
            kernelState.addHIDEvent(HIDEventEnum.PRIMARY_NO_DIRECTION);
        } else {
            kernelState.removeHIDEvent(HIDEventEnum.PRIMARY_NO_DIRECTION);
        }
    }
}
