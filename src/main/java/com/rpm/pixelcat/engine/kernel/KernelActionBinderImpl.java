package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;

import java.util.HashMap;

class KernelActionBinderImpl implements KernelActionBinder {
    private HashMap<HIDEventEnum, KernelActionEnum> bindings;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelActionBinderImpl.class);

    KernelActionBinderImpl() {
        // init
        bindings = new HashMap<>();

        // default bindings
        bind(HIDEventEnum.ESC, KernelActionEnum.EXIT);
        bind(HIDEventEnum.F, KernelActionEnum.FONT_DEBUG_TOGGLE);
        bind(HIDEventEnum.SIX, KernelActionEnum.SET_LOG_LVL_TRACE);
        bind(HIDEventEnum.FIVE, KernelActionEnum.SET_LOG_LVL_DEBUG);
        bind(HIDEventEnum.FOUR, KernelActionEnum.SET_LOG_LVL_INFO);
        bind(HIDEventEnum.THREE, KernelActionEnum.SET_LOG_LVL_WARN);
        bind(HIDEventEnum.TWO, KernelActionEnum.SET_LOG_LVL_ERROR);
        bind(HIDEventEnum.ONE, KernelActionEnum.SET_LOG_LVL_FATAL);
    }

    public KernelActionEnum binding(HIDEventEnum key) throws GameException {
        // look up kernel action
        KernelActionEnum kernelAction = bindings.get(key);

        // check result
        if (kernelAction == null) {
            throw new GameException(GameErrorCode.KERNEL_ACTION_UNSUPPORTED);
        }

        return kernelAction;
    }

    public HIDEventEnum binding(KernelActionEnum kernelAction) throws GameException {
        // look up hid event
        if (bindings.containsValue(kernelAction)) {
            for (HIDEventEnum key : bindings.keySet()) {
                if (bindings.get(key).equals(kernelAction)) {
                    return key;
                }
            }
        }

        // throw error if not found
        throw new GameException(GameErrorCode.KERNEL_ACTION_UNSUPPORTED);
    }

    public void bind(HIDEventEnum key, KernelActionEnum kernelAction) {
        // debug
        PRINTER.printInfo("Binding " + key + " to " + kernelAction);

        // save kernel action binding
        bindings.put(key, kernelAction);
    }

    public void unbind(HIDEventEnum key) {
        // look up binding
        KernelActionEnum kernelAction = bindings.get(key);

        // debug
        PRINTER.printInfo("Unbinding " + key + " from " + kernelAction);

        // remove kernel action binding
        bindings.remove(key);
    }
}
