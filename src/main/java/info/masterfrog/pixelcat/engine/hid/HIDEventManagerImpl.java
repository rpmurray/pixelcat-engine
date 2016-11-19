package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.InternalUnexpectedLogicException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

class HIDEventManagerImpl implements HIDEventManager {
    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(HIDEventManagerImpl.class);

    public HIDEventManagerImpl() {
    }

    public void handleKeyboardEvent(HIDEventTypeEnum hidEventType, KeyEvent keyEvent) throws TransientGameException {
        // map HID event
        try {
            HIDEvent hidEvent = mapKeyboardEvent(keyEvent);

            // determine kernelState impact
            switch (hidEventType) {
                case PRESS:
                    KernelState.getInstance().addHIDTriggeredEvent(hidEvent);
                    break;
                case RELEASE:
                    KernelState.getInstance().removeHIDEvent(hidEvent);
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
            HIDEvent hidEvent = mapMouseEvent(mouseEvent);

            // determine kernelState impact
            switch (hidEventType) {
                case PRESS:
                    KernelState.getInstance().addHIDTriggeredEvent(hidEvent);
                    break;
                case RELEASE:
                    KernelState.getInstance().removeHIDEvent(hidEvent);
                    break;
                case SCROLL:
                    KernelState.getInstance().addHIDTriggeredEvent(hidEvent);
                    break;
                case DRAG:
                default:
                    throw new InternalUnexpectedLogicException(GameEngineErrorCode.HID_EVENT_TYPE_UNSUPPORTED);
            }
        } catch (TransientGameException e) {
            PRINTER.printInfo(e.toString() + " [" + mouseEvent + "," + hidEventType + "]");
        }
    }

    private HIDEvent mapKeyboardEvent(KeyEvent keyEvent) throws TransientGameException {
        // setup
        HIDEvent hidEvent;

        // fetch hid event bindings
        HIDEventBinder hidEventBinder = KernelState.getInstance().getProperty(KernelStateProperty.HID_EVENT_BINDER);

        // derive key code from key event
        Integer keyCode = keyEvent.getKeyCode();

        // handle key code triggered hid events
        hidEvent = hidEventBinder.binding(keyCode);

        return hidEvent;
    }

    private HIDEvent mapMouseEvent(MouseEvent mouseEvent) throws TransientGameException {
        // setup
        HIDEvent hidEvent;

        // fetch hid event bindings
        HIDEventBinder hidEventBinder = KernelState.getInstance().getProperty(KernelStateProperty.HID_EVENT_BINDER);

        // derive button code from mouse event
        Integer buttonCode = mouseEvent.getButton();

        // handle mouse triggered hid events
        if (buttonCode.equals(MouseEvent.MOUSE_WHEEL)) {
            MouseWheelEvent mouseWheelEvent = (MouseWheelEvent) mouseEvent;
            Double magnitude = mouseWheelEvent.getPreciseWheelRotation();
            if (magnitude > 0) {
                hidEvent = HIDEvent.SCROLL_UP;
                hidEvent.value("magnitude", Math.abs(magnitude));
            } else {
                hidEvent = HIDEvent.SCROLL_DOWN;
                hidEvent.value("magnitude", Math.abs(magnitude));
            }
        } else {
            hidEvent = hidEventBinder.binding(buttonCode);
        }

        return hidEvent;
    }

    public void generateSynthesizedEvents() {
        // no directional keyboard events
        if (!KernelState.getInstance().hasHIDTriggeredEvent(HIDEvent.PRIMARY_UP) &&
            !KernelState.getInstance().hasHIDTriggeredEvent(HIDEvent.PRIMARY_DOWN) &&
            !KernelState.getInstance().hasHIDTriggeredEvent(HIDEvent.PRIMARY_LEFT) &&
            !KernelState.getInstance().hasHIDTriggeredEvent(HIDEvent.PRIMARY_RIGHT)) {
            KernelState.getInstance().addHIDTriggeredEvent(HIDEvent.PRIMARY_NO_DIRECTION);
        } else {
            KernelState.getInstance().removeHIDSustainedEvent(HIDEvent.PRIMARY_NO_DIRECTION);
        }
    }
}
