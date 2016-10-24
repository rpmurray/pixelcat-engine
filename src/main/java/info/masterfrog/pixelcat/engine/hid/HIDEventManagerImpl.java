package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.InternalUnexpectedLogicException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStatePropertyEnum;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

class HIDEventManagerImpl implements HIDEventManager {
    private final KernelState kernelState;
    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(HIDEventManagerImpl.class);

    public HIDEventManagerImpl(KernelState kernelState) {
        this.kernelState = kernelState;
    }

    public void handleKeyboardEvent(HIDEventTypeEnum hidEventType, KeyEvent keyEvent) throws TransientGameException {
        // map HID event
        try {
            HIDEventEnum hidEvent = mapKeyboardEvent(keyEvent);

            // determine kernelState impact
            switch (hidEventType) {
                case PRESS:
                    kernelState.addHIDTriggeredEvent(hidEvent);
                    break;
                case RELEASE:
                    kernelState.removeHIDEvent(hidEvent);
                    break;
                default:
                    throw new InternalUnexpectedLogicException(GameEngineErrorCode.HID_EVENT_TYPE_UNSUPPORTED);
            }
        } catch (TransientGameException e) {
            PRINTER.printInfo(e.toString() + " [" + keyEvent + "," + hidEventType + "]");
        }
    }

    public void handleMouseEvent(HIDEventTypeEnum hidEventType, MouseEvent mouseEvent) throws TransientGameException {
        // map HID event
        try {
            HIDEventEnum hidEvent = mapMouseEvent(mouseEvent);

            // determine kernelState impact
            switch (hidEventType) {
                case PRESS:
                    kernelState.addHIDTriggeredEvent(hidEvent);
                    break;
                case RELEASE:
                    kernelState.removeHIDEvent(hidEvent);
                    break;
                case SCROLL:
                    kernelState.addHIDTriggeredEvent(hidEvent);
                    break;
                case DRAG:
                default:
                    throw new InternalUnexpectedLogicException(GameEngineErrorCode.HID_EVENT_TYPE_UNSUPPORTED);
            }
        } catch (TransientGameException e) {
            PRINTER.printInfo(e.toString() + " [" + mouseEvent + "," + hidEventType + "]");
        }
    }

    private HIDEventEnum mapKeyboardEvent(KeyEvent keyEvent) throws TransientGameException {
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

    private HIDEventEnum mapMouseEvent(MouseEvent mouseEvent) throws TransientGameException {
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
        if (!kernelState.hasHIDTriggeredEvent(HIDEventEnum.PRIMARY_UP) &&
            !kernelState.hasHIDTriggeredEvent(HIDEventEnum.PRIMARY_DOWN) &&
            !kernelState.hasHIDTriggeredEvent(HIDEventEnum.PRIMARY_LEFT) &&
            !kernelState.hasHIDTriggeredEvent(HIDEventEnum.PRIMARY_RIGHT)) {
            kernelState.addHIDTriggeredEvent(HIDEventEnum.PRIMARY_NO_DIRECTION);
        } else {
            kernelState.removeHIDSustainedEvent(HIDEventEnum.PRIMARY_NO_DIRECTION);
        }
    }
}
