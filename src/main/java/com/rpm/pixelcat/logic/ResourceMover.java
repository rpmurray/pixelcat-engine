package com.rpm.pixelcat.logic;

import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.logic.resource.model.ResourceImpl;

import java.awt.*;
import java.util.HashSet;

public class ResourceMover {
    private static ResourceMover instance;
    private static final Printer PRINTER = new Printer();

    private ResourceMover() {
    }

    public static ResourceMover getInstace() {
        if (instance == null) {
            instance = new ResourceMover();
        }

        return instance;
    }

    public void move(ResourceImpl object, KernelState kernelState, ImmutableSet<HIDEventEnum> applicableEvents) {
        Double xVel = 0.0, yVel = 0.0;
        Double magnitude = object.getMagnitude();
        HashSet<HIDEventEnum> hidEvents = kernelState.getHIDEvents();
        Rectangle bounds = kernelState.getBounds();

        // determine velocity
        for (HIDEventEnum hidEvent : hidEvents) {
            // only apply HID events of interest to this resource
            if (!applicableEvents.contains(hidEvent)) {
                continue;
            }

            // map event to velocity changes
            switch (hidEvent) {
                case UP:
                    yVel = -magnitude;
                    break;
                case DOWN:
                    yVel = magnitude;
                    break;
                case RIGHT:
                    xVel = magnitude;
                    break;
                case LEFT:
                    xVel = -magnitude;
                    break;
                default:
                    // do nothing
                    break;
            }

            // debug
            if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.DEBUG_ENABLED)) {
                PRINTER.printDebug("K:" + hidEvent);
            }
        }

        // move object
        object.x += xVel;
        object.y += yVel;

        // bounds checking
        if ((object.y + object.height) > bounds.height) {
            object.y = bounds.height - object.height;
        }
        if ((object.x + object.width) > bounds.width) {
            object.x = bounds.width - object.width;
        }
        if (object.y < bounds.y) {
            object.y = bounds.y;
        }
        if (object.x < bounds.x) {
            object.x = bounds.x;
        }

        // debug
        if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.DEBUG_ENABLED)) {
            PRINTER.printDebug(" B: " + bounds + " X:" + object.x + " Y:" + object.y);
        }
    }

}
