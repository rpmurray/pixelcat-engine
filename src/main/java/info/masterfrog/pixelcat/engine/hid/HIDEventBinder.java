package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

public interface HIDEventBinder {
    public HIDEvent binding(Integer key) throws TransientGameException;

    public Integer binding(HIDEvent value) throws TransientGameException;

    public void bind(Integer key, HIDEvent hidEvent);

    public void unbind(Integer key);

    public static HIDEventBinder create() {
        HIDEventBinder hidEventBinder = new HIDEventBinderImpl();

        return hidEventBinder;
    }
}
