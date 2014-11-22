package com.rpm.pixelcat.kernel;

import com.rpm.pixelcat.hid.HIDEventEnum;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class KernelState {
    private HashSet<HIDEventEnum> hidEvents;
    private HashSet<Exception> errors;
    private HashMap<KernelStatePropertyEnum, Object> properties;
    private Rectangle bounds;

    public KernelState(Rectangle bounds) {
        this.hidEvents = new HashSet<>();
        this.errors = new HashSet<>();
        this.properties = new HashMap<>();
        this.bounds = bounds;
    }

    public void init() {
        setProperty(KernelStatePropertyEnum.DEBUG_ENABLED, true);
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

    public void setProperty(KernelStatePropertyEnum name, Object value) {
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
}
