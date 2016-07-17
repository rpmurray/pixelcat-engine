package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventEnum;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockManager;

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

    public void addTerminalError(Exception exception);

    public Boolean hasTerminalErrors();

    public HashSet<Exception> getTerminalErrors();

    public void addTransientError(Exception exception);

    public void clearTransientErrors();

    public Boolean hasTransientErrors();

    public HashSet<Exception> getTransientErrors();

    public void setProperty(KernelStatePropertyEnum name, Object value) throws TransientGameException;

    public Object getProperty(KernelStatePropertyEnum name);

    public Boolean getPropertyFlag(KernelStatePropertyEnum name);

    public void setBounds(Rectangle bounds);

    public Rectangle getBounds();

    public GameClockManager getMasterGameClockManager();
}
