package com.rpm.pixelcat.logic;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.logic.gameobject.GameObject;
import com.rpm.pixelcat.logic.resource.MovableResource;
import com.rpm.pixelcat.logic.resource.Resource;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GameObjectMover {
    private static GameObjectMover instance;
    private static final Printer PRINTER = new Printer();

    private GameObjectMover() {
    }

    public static GameObjectMover getInstace() {
        if (instance == null) {
            instance = new GameObjectMover();
        }

        return instance;
    }

    public void move(GameObject gameObject, KernelState kernelState, Set<HIDEventEnum> applicableEvents) {
        Double xVel = 0.0, yVel = 0.0;
        Double magnitude = 1.0;
        HashSet<HIDEventEnum> hidEvents = kernelState.getHIDEvents();
        Rectangle screenBounds = kernelState.getBounds();

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

        // initialize moved position
        Point position = gameObject.getPosition();
        Double x = position.getX() + xVel;
        Double y = position.getY() + yVel;

        // screen bounds checking
        Resource resource = gameObject.getCurrentResource();
        if (resource instanceof MovableResource) {
            Rectangle resourceBounds = ((MovableResource) resource).getCelBounds();
            if ((position.getX() + resourceBounds.getWidth()) > screenBounds.getWidth()) {
                x = screenBounds.getWidth() - resourceBounds.getWidth();
            }
            if ((position.getY() + resourceBounds.getHeight()) > screenBounds.getHeight()) {
                y = screenBounds.getHeight() - resourceBounds.getHeight();
            }
            if (y < screenBounds.getY()) {
                y = screenBounds.getY();
            }
            if (x < screenBounds.getX()) {
                x = screenBounds.getX();
            }

            // set position
            position.setLocation(x, y);
            gameObject.setPosition(position);
        }

        // debug
        if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.DEBUG_ENABLED)) {
            PRINTER.printDebug(" SB: " + screenBounds + " GOP:" + position);
        }
    }

}
