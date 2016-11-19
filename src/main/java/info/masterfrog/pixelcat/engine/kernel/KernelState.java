package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockManager;

import java.util.HashSet;

public interface KernelState {
    void addHIDTriggeredEvent(HIDEvent hidEvent);

    void removeHIDEvent(HIDEvent hidEvent);

    void removeHIDTriggeredEvent(HIDEvent hidEvent);

    void removeHIDSustainedEvent(HIDEvent hidEvent);

    Boolean hasHIDTriggeredEvent(HIDEvent event);

    Boolean hasHIDSustainedEvent(HIDEvent hidEvent);

    HashSet<HIDEvent> getHIDTriggeredEvents();

    HashSet<HIDEvent> getHIDSustainedEvents();

    void addKernelAction(KernelAction kernelAction);

    void removeKernelAction(KernelAction kernelAction);

    void clearKernelActions();

    void resetTransientKernelActions();

    Boolean hasKernelAction(KernelAction kernelAction);

    HashSet<KernelAction> getKernelActions();

    void addTerminalError(Exception exception);

    Boolean hasTerminalErrors();

    HashSet<Exception> getTerminalErrors();

    void addTransientError(Exception exception);

    void clearTransientErrors();

    Boolean hasTransientErrors();

    HashSet<Exception> getTransientErrors();

    void setProperty(KernelStateProperty name, Object value) throws TransientGameException;

    <T> T getProperty(KernelStateProperty name);

    Boolean getPropertyFlag(KernelStateProperty name);

    GameClockManager getMasterGameClockManager();

    static KernelState getInstance() {
        return KernelStateImpl.getInstance();
    }
}
