package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventEnum;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockManager;

import java.awt.*;
import java.util.HashSet;

public interface KernelState {
    // game clock names
    String MASTER_GAME_CLOCK = "masterClock";
    String LOOP_GAME_CLOCK = "loopClock";

    void addHIDTriggeredEvent(HIDEventEnum hidEvent);

    void removeHIDEvent(HIDEventEnum hidEvent);

    void removeHIDTriggeredEvent(HIDEventEnum hidEvent);

    void removeHIDSustainedEvent(HIDEventEnum hidEvent);

    Boolean hasHIDTriggeredEvent(HIDEventEnum event);

    Boolean hasHIDSustainedEvent(HIDEventEnum hidEvent);

    HashSet<HIDEventEnum> getHIDTriggeredEvents();

    HashSet<HIDEventEnum> getHIDSustainedEvents();

    void addKernelAction(KernelActionEnum kernelAction);

    void removeKernelAction(KernelActionEnum kernelAction);

    void clearKernelActions();

    void resetTransientKernelActions();

    Boolean hasKernelAction(KernelActionEnum kernelAction);

    HashSet<KernelActionEnum> getKernelActions();

    void addTerminalError(Exception exception);

    Boolean hasTerminalErrors();

    HashSet<Exception> getTerminalErrors();

    void addTransientError(Exception exception);

    void clearTransientErrors();

    Boolean hasTransientErrors();

    HashSet<Exception> getTransientErrors();

    void setProperty(KernelStatePropertyEnum name, Object value) throws TransientGameException;

    Object getProperty(KernelStatePropertyEnum name);

    Boolean getPropertyFlag(KernelStatePropertyEnum name);

    void setBounds(Rectangle bounds);

    Rectangle getBounds();

    GameClockManager getMasterGameClockManager();
}
