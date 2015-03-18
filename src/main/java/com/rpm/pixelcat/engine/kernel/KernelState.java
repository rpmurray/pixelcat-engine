package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;

import java.awt.*;
import java.util.HashSet;

public interface KernelState {
    public void addHIDEvent(HIDEventEnum hidEvent);

    public void removeHIDEvent(HIDEventEnum hidEvent);

    public void clearHIDEvents();

    public Boolean hasHIDEvent(HIDEventEnum event);

    public HashSet<HIDEventEnum> getHIDEvents();

    public void addError(Exception exception);

    public void clearErrors();

    public HashSet<Exception> getErrors();

    public void setProperty(KernelStatePropertyEnum name, Object value) throws GameException;

    public Object getProperty(KernelStatePropertyEnum name);

    public Boolean getPropertyFlag(KernelStatePropertyEnum name);

    public Rectangle getBounds();

    public Long getClockTime();
}
