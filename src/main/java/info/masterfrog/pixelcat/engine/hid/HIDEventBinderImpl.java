package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

class HIDEventBinderImpl implements HIDEventBinder {
    private HashMap<Integer, HIDEvent> bindings;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(HIDEventBinderImpl.class);

    HIDEventBinderImpl() {
        bindings = new HashMap<>();
        bind(KeyEvent.VK_UP, HIDEvent.PRIMARY_UP);
        bind(KeyEvent.VK_DOWN, HIDEvent.PRIMARY_DOWN);
        bind(KeyEvent.VK_LEFT, HIDEvent.PRIMARY_LEFT);
        bind(KeyEvent.VK_RIGHT, HIDEvent.PRIMARY_RIGHT);
        bind(KeyEvent.VK_ENTER, HIDEvent.ENTER);
        bind(KeyEvent.VK_SPACE, HIDEvent.SPACE);
        bind(KeyEvent.VK_ESCAPE, HIDEvent.ESC);
        bind(KeyEvent.VK_DELETE, HIDEvent.DELETE);
        bind(MouseEvent.BUTTON1, HIDEvent.CLICK_LEFT);
        bind(MouseEvent.BUTTON2, HIDEvent.CLICK_MIDDLE);
        bind(MouseEvent.BUTTON3, HIDEvent.CLICK_RIGHT);
        bind(KeyEvent.VK_A, HIDEvent.A);
        bind(KeyEvent.VK_B, HIDEvent.B);
        bind(KeyEvent.VK_C, HIDEvent.C);
        bind(KeyEvent.VK_D, HIDEvent.D);
        bind(KeyEvent.VK_E, HIDEvent.E);
        bind(KeyEvent.VK_F, HIDEvent.F);
        bind(KeyEvent.VK_G, HIDEvent.G);
        bind(KeyEvent.VK_H, HIDEvent.H);
        bind(KeyEvent.VK_I, HIDEvent.I);
        bind(KeyEvent.VK_J, HIDEvent.J);
        bind(KeyEvent.VK_K, HIDEvent.K);
        bind(KeyEvent.VK_L, HIDEvent.L);
        bind(KeyEvent.VK_M, HIDEvent.M);
        bind(KeyEvent.VK_N, HIDEvent.N);
        bind(KeyEvent.VK_O, HIDEvent.O);
        bind(KeyEvent.VK_P, HIDEvent.P);
        bind(KeyEvent.VK_Q, HIDEvent.Q);
        bind(KeyEvent.VK_R, HIDEvent.R);
        bind(KeyEvent.VK_S, HIDEvent.S);
        bind(KeyEvent.VK_T, HIDEvent.T);
        bind(KeyEvent.VK_U, HIDEvent.U);
        bind(KeyEvent.VK_V, HIDEvent.V);
        bind(KeyEvent.VK_W, HIDEvent.W);
        bind(KeyEvent.VK_X, HIDEvent.X);
        bind(KeyEvent.VK_Y, HIDEvent.Y);
        bind(KeyEvent.VK_Z, HIDEvent.Z);
        bind(KeyEvent.VK_0, HIDEvent.ZERO);
        bind(KeyEvent.VK_1, HIDEvent.ONE);
        bind(KeyEvent.VK_2, HIDEvent.TWO);
        bind(KeyEvent.VK_3, HIDEvent.THREE);
        bind(KeyEvent.VK_4, HIDEvent.FOUR);
        bind(KeyEvent.VK_5, HIDEvent.FIVE);
        bind(KeyEvent.VK_6, HIDEvent.SIX);
        bind(KeyEvent.VK_7, HIDEvent.SEVEN);
        bind(KeyEvent.VK_8, HIDEvent.EIGHT);
        bind(KeyEvent.VK_9, HIDEvent.NINE);
    }

    public HIDEvent binding(Integer key) throws TransientGameException {
        // look up hid event
        HIDEvent hidEvent = bindings.get(key);

        // check result
        if (hidEvent == null) {
            throw new TransientGameException(GameEngineErrorCode.HID_EVENT_UNSUPPORTED);
        }

        return hidEvent;
    }

    public Integer binding(HIDEvent hidEvent) throws TransientGameException {
        // look up hid event
        if (bindings.containsValue(hidEvent)) {
            for (Integer key : bindings.keySet()) {
                if (bindings.get(key).equals(hidEvent)) {
                    return key;
                }
            }
        }

        // throw error if not found
        throw new TransientGameException(GameEngineErrorCode.HID_EVENT_UNSUPPORTED);
    }

    public void bind(Integer key, HIDEvent hidEvent) {
        // debug
        PRINTER.printInfo("Binding " + key + " to " + hidEvent);

        // save hid event binding
        bindings.put(key, hidEvent);
    }

    public void unbind(Integer key) {
        // look up binding
        HIDEvent hidEvent = bindings.get(key);

        // debug
        PRINTER.printInfo("Unbinding " + key + " from " + hidEvent);

        // remove hid event binding
        bindings.remove(key);
    }
}
