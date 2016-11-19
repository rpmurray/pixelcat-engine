package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;

public interface KernelActionBinder {
    public KernelAction resolveBinding(HIDEvent key) throws TransientGameException;

    public HIDEvent resolveBinding(KernelAction kernelAction) throws TransientGameException;

    public void bind(HIDEvent key, KernelAction kernelAction);

    public void unbind(HIDEvent key);

    public static KernelActionBinder create() {
        KernelActionBinder kernelActionBinder = new KernelActionBinderImpl();

        return kernelActionBinder;
    }
}
