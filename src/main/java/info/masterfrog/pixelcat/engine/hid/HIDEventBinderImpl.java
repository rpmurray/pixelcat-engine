package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

class HIDEventBinderImpl implements HIDEventBinder {
    private HashMap<Integer, HIDEventEnum> bindings;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(HIDEventBinderImpl.class);

    HIDEventBinderImpl() {
        bindings = new HashMap<>();
        bind(KeyEvent.VK_UP, HIDEventEnum.PRIMARY_UP);
        bind(KeyEvent.VK_DOWN, HIDEventEnum.PRIMARY_DOWN);
        bind(KeyEvent.VK_LEFT, HIDEventEnum.PRIMARY_LEFT);
        bind(KeyEvent.VK_RIGHT, HIDEventEnum.PRIMARY_RIGHT);
        bind(KeyEvent.VK_ENTER, HIDEventEnum.ENTER);
        bind(KeyEvent.VK_SPACE, HIDEventEnum.SPACE);
        bind(KeyEvent.VK_ESCAPE, HIDEventEnum.ESC);
        bind(KeyEvent.VK_DELETE, HIDEventEnum.DELETE);
        bind(MouseEvent.BUTTON1, HIDEventEnum.CLICK_LEFT);
        bind(MouseEvent.BUTTON2, HIDEventEnum.CLICK_MIDDLE);
        bind(MouseEvent.BUTTON3, HIDEventEnum.CLICK_RIGHT);
        bind(KeyEvent.VK_A, HIDEventEnum.A);
        bind(KeyEvent.VK_B, HIDEventEnum.B);
        bind(KeyEvent.VK_C, HIDEventEnum.C);
        bind(KeyEvent.VK_D, HIDEventEnum.D);
        bind(KeyEvent.VK_E, HIDEventEnum.E);
        bind(KeyEvent.VK_F, HIDEventEnum.F);
        bind(KeyEvent.VK_G, HIDEventEnum.G);
        bind(KeyEvent.VK_H, HIDEventEnum.H);
        bind(KeyEvent.VK_I, HIDEventEnum.I);
        bind(KeyEvent.VK_J, HIDEventEnum.J);
        bind(KeyEvent.VK_K, HIDEventEnum.K);
        bind(KeyEvent.VK_L, HIDEventEnum.L);
        bind(KeyEvent.VK_M, HIDEventEnum.M);
        bind(KeyEvent.VK_N, HIDEventEnum.N);
        bind(KeyEvent.VK_O, HIDEventEnum.O);
        bind(KeyEvent.VK_P, HIDEventEnum.P);
        bind(KeyEvent.VK_Q, HIDEventEnum.Q);
        bind(KeyEvent.VK_R, HIDEventEnum.R);
        bind(KeyEvent.VK_S, HIDEventEnum.S);
        bind(KeyEvent.VK_T, HIDEventEnum.T);
        bind(KeyEvent.VK_U, HIDEventEnum.U);
        bind(KeyEvent.VK_V, HIDEventEnum.V);
        bind(KeyEvent.VK_W, HIDEventEnum.W);
        bind(KeyEvent.VK_X, HIDEventEnum.X);
        bind(KeyEvent.VK_Y, HIDEventEnum.Y);
        bind(KeyEvent.VK_Z, HIDEventEnum.Z);
        bind(KeyEvent.VK_0, HIDEventEnum.ZERO);
        bind(KeyEvent.VK_1, HIDEventEnum.ONE);
        bind(KeyEvent.VK_2, HIDEventEnum.TWO);
        bind(KeyEvent.VK_3, HIDEventEnum.THREE);
        bind(KeyEvent.VK_4, HIDEventEnum.FOUR);
        bind(KeyEvent.VK_5, HIDEventEnum.FIVE);
        bind(KeyEvent.VK_6, HIDEventEnum.SIX);
        bind(KeyEvent.VK_7, HIDEventEnum.SEVEN);
        bind(KeyEvent.VK_8, HIDEventEnum.EIGHT);
        bind(KeyEvent.VK_9, HIDEventEnum.NINE);
    }

    public HIDEventEnum binding(Integer key) throws TransientGameException {
        // look up hid event
        HIDEventEnum hidEvent = bindings.get(key);

        // check result
        if (hidEvent == null) {
            throw new TransientGameException(GameEngineErrorCode.HID_EVENT_UNSUPPORTED);
        }

        return hidEvent;
    }

    public Integer binding(HIDEventEnum hidEvent) throws TransientGameException {
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

    public void bind(Integer key, HIDEventEnum hidEvent) {
        // debug
        PRINTER.printInfo("Binding " + key + " to " + hidEvent);

        // save hid event binding
        bindings.put(key, hidEvent);
    }

    public void unbind(Integer key) {
        // look up binding
        HIDEventEnum hidEvent = bindings.get(key);

        // debug
        PRINTER.printInfo("Unbinding " + key + " from " + hidEvent);

        // remove hid event binding
        bindings.remove(key);
    }
}
