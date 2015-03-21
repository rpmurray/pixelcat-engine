package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.logic.clock.GameClockFactory;
import com.rpm.pixelcat.engine.logic.clock.GameClockManager;
import org.apache.log4j.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class KernelStateImpl implements KernelState {
    private HashSet<HIDEventEnum> hidEvents;
    private HashSet<Exception> errors;
    private HashMap<KernelStatePropertyEnum, Object> properties;
    private Rectangle bounds;
    private GameClockManager masterGameClockManager;

    KernelStateImpl(Rectangle bounds) {
        this.hidEvents = new HashSet<>();
        this.errors = new HashSet<>();
        this.properties = new HashMap<>();
        this.bounds = bounds;
        this.masterGameClockManager = GameClockFactory.getInstance().createGameClockManager();
    }

    void init() throws GameException {
        // create master game clocks
        masterGameClockManager.addGameClock(MASTER_GAME_CLOCK);
        masterGameClockManager.addGameClock(LOOP_GAME_CLOCK);

        // set properties
        setProperty(KernelStatePropertyEnum.FRAME_RATE, 60);
        setProperty(KernelStatePropertyEnum.FONT_DISPLAY_ENABLED, false);
        setProperty(KernelStatePropertyEnum.EXIT_SIGNAL, false);
        setProperty(KernelStatePropertyEnum.LOG_LVL, Level.WARN);
        setProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS, new ArrayList<>());
    }

    public void addHIDEvent(HIDEventEnum hidEvent) {
        this.hidEvents.add(hidEvent);
    }

    public void removeHIDEvent(HIDEventEnum hidEvent) {
        this.hidEvents.remove(hidEvent);
    }

    public void clearHIDEvents() {
        hidEvents.clear();
    }

    public Boolean hasHIDEvent(HIDEventEnum event) {
        return this.hidEvents.contains(event);
    }

    public HashSet<HIDEventEnum> getHIDEvents() {
        return this.hidEvents;
    }

    public void addError(Exception exception) {
        errors.add(exception);
    }

    public void clearErrors() {
        errors.clear();
    }

    public HashSet<Exception> getErrors() {
        return errors;
    }

    public void setProperty(KernelStatePropertyEnum name, Object value) throws GameException {
        // property-specific handling
        switch (name) {
            case GAME_LOOP_DURATION_MS:
                // do not allow these cases to be set manually
                throw new GameException(GameErrorCode.LOGIC_ERROR);
            case FRAME_RATE:
                // validate
                if ((Integer) value > 1000) {
                    throw new GameException(GameErrorCode.LOGIC_ERROR);
                }

                // set loop time
                properties.put(KernelStatePropertyEnum.GAME_LOOP_DURATION_MS, 1000 / (Integer) value);
                break;
            case LOG_LVL:
                // validate input
                if (!(value instanceof Level)) {
                    throw new GameException(GameErrorCode.LOGIC_ERROR);
                }

                // update logging level in printer
                Printer.setLevel((Level) value);
                break;
            default:
                // do nothing special
                break;
        }

        // save property value
        properties.put(name, value);
    }

    public Object getProperty(KernelStatePropertyEnum name) {
        Object value = null;
        if (properties.containsKey(name)) {
            value = properties.get(name);
        }

        return value;
    }

    public Boolean getPropertyFlag(KernelStatePropertyEnum name) {
        Boolean value = false;
        if (properties.containsKey(name)) {
            value = (Boolean) properties.get(name);
        }

        return value;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public GameClockManager getMasterGameClockManager() {
        return masterGameClockManager;
    }
}
