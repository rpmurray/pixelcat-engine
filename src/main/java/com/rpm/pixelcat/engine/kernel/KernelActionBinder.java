package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;

public interface KernelActionBinder {
    public KernelActionEnum binding(HIDEventEnum key) throws TransientGameException;

    public HIDEventEnum binding(KernelActionEnum kernelAction) throws TransientGameException;

    public void bind(HIDEventEnum key, KernelActionEnum kernelAction);

    public void unbind(HIDEventEnum key);

    public static KernelActionBinder create() {
        KernelActionBinder kernelActionBinder = new KernelActionBinderImpl();

        return kernelActionBinder;
    }
}
