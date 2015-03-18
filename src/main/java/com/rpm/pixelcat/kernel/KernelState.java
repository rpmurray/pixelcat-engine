package com.rpm.pixelcat.kernel;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.exception.GameErrorCode;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.hid.HIDEventEnum;
import org.apache.log4j.Level;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class KernelState {
    private HashSet<HIDEventEnum> hidEvents;
    private HashSet<Exception> errors;
    private HashMap<KernelStatePropertyEnum, Object> properties;
    private Rectangle bounds;
    private Long clockTime;

    public KernelState(Rectangle bounds, Long clockTime) {
        this.hidEvents = new HashSet<>();
        this.errors = new HashSet<>();
        this.properties = new HashMap<>();
        this.bounds = bounds;
        this.clockTime = clockTime;
    }

    public void init() throws GameException {
        setProperty(KernelStatePropertyEnum.LOG_LVL, Level.WARN);
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

    public Boolean getPropertyAsBoolean(KernelStatePropertyEnum name) {
        Boolean value = false;
        if (properties.containsKey(name)) {
            value = (Boolean) properties.get(name);
        }

        return value;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Long getClockTime() {
        return clockTime;
    }

    public void setClockTime(Long clockTime) {
        this.clockTime = clockTime;
    }
}
