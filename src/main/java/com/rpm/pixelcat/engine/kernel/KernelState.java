package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.logic.clock.GameClockManager;

import java.awt.*;
import java.util.HashSet;

public interface KernelState {
    // game clock names
    public static final String MASTER_GAME_CLOCK = "masterClock";
    public static final String LOOP_GAME_CLOCK = "loopClock";

    public void addHIDEvent(HIDEventEnum hidEvent);

    public void removeHIDEvent(HIDEventEnum hidEvent);

    public void clearHIDEvents();

    public Boolean hasHIDEvent(HIDEventEnum event);

    public HashSet<HIDEventEnum> getHIDEvents();

    public void addKernelAction(KernelActionEnum kernelAction);

    public void removeKernelAction(KernelActionEnum kernelAction);

    public void clearKernelActions();

    public void resetTransientKernelActions();

    public Boolean hasKernelAction(KernelActionEnum kernelAction);

    public HashSet<KernelActionEnum> getKernelActions();

    public void addError(Exception exception);

    public void clearErrors();

    public HashSet<Exception> getErrors();

    public void setProperty(KernelStatePropertyEnum name, Object value) throws GameException;

    public Object getProperty(KernelStatePropertyEnum name);

    public Boolean getPropertyFlag(KernelStatePropertyEnum name);

    public void setBounds(Rectangle bounds);

    public Rectangle getBounds();

    public GameClockManager getMasterGameClockManager();
}
