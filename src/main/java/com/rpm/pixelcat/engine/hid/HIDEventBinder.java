package com.rpm.pixelcat.engine.hid;

import com.rpm.pixelcat.engine.exception.TransientGameException;

public interface HIDEventBinder {
    public HIDEventEnum binding(Integer key) throws TransientGameException;

    public Integer binding(HIDEventEnum value) throws TransientGameException;

    public void bind(Integer key, HIDEventEnum hidEvent);

    public void unbind(Integer key);

    public static HIDEventBinder create() {
        HIDEventBinder hidEventBinder = new HIDEventBinderImpl();

        return hidEventBinder;
    }
}
