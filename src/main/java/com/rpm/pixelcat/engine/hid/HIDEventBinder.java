package com.rpm.pixelcat.engine.hid;

import com.rpm.pixelcat.engine.exception.GameException;

public interface HIDEventBinder {
    public HIDEventEnum binding(Integer key) throws GameException;

    public Integer binding(HIDEventEnum value) throws GameException;

    public void bind(Integer key, HIDEventEnum hidEvent);

    public void unbind(Integer key);

    public static HIDEventBinder create() {
        HIDEventBinder hidEventBinder = new HIDEventBinderImpl();

        return hidEventBinder;
    }
}
