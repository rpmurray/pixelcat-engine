package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;

import java.util.HashMap;

class KernelActionBinderImpl implements KernelActionBinder {
    private HashMap<HIDEvent, KernelAction> bindings;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelActionBinderImpl.class);

    KernelActionBinderImpl() {
        // init
        bindings = new HashMap<>();

        // default bindings
        bind(HIDEvent.ESC, KernelAction.EXIT);
        bind(HIDEvent.F, KernelAction.FONT_DEBUG_TOGGLE);
        bind(HIDEvent.SIX, KernelAction.SET_LOG_LVL_TRACE);
        bind(HIDEvent.FIVE, KernelAction.SET_LOG_LVL_DEBUG);
        bind(HIDEvent.FOUR, KernelAction.SET_LOG_LVL_INFO);
        bind(HIDEvent.THREE, KernelAction.SET_LOG_LVL_WARN);
        bind(HIDEvent.TWO, KernelAction.SET_LOG_LVL_ERROR);
        bind(HIDEvent.ONE, KernelAction.SET_LOG_LVL_FATAL);
    }

    public KernelAction resolveBinding(HIDEvent key) throws TransientGameException {
        // look up kernel action
        KernelAction kernelAction = bindings.get(key);

        // check result
        if (kernelAction == null) {
            throw new TransientGameException(GameEngineErrorCode.KERNEL_ACTION_UNSUPPORTED);
        }

        return kernelAction;
    }

    public HIDEvent resolveBinding(KernelAction kernelAction) throws TransientGameException {
        // look up hid event
        if (bindings.containsValue(kernelAction)) {
            for (HIDEvent key : bindings.keySet()) {
                if (bindings.get(key).equals(kernelAction)) {
                    return key;
                }
            }
        }

        // throw error if not found
        throw new TransientGameException(GameEngineErrorCode.KERNEL_ACTION_UNSUPPORTED);
    }

    public void bind(HIDEvent key, KernelAction kernelAction) {
        // debug
        PRINTER.printInfo("Binding " + key + " to " + kernelAction);

        // save kernel action binding
        bindings.put(key, kernelAction);
    }

    public void unbind(HIDEvent key) {
        // look up binding
        KernelAction kernelAction = bindings.get(key);

        // debug
        PRINTER.printInfo("Unbinding " + key + " from " + kernelAction);

        // remove kernel action binding
        bindings.remove(key);
    }
}
