package com.rpm.pixelcat.logic;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.logic.animation.AnimationSequence;
import com.rpm.pixelcat.logic.gameobject.GameObject;
import com.rpm.pixelcat.logic.gameobject.GameObjectHIDEventLogicBehaviorBinding;
import com.rpm.pixelcat.logic.gameobject.GameObjectLogicBehavior;
import com.rpm.pixelcat.logic.gameobject.GameObjectLogicParameterOrientation;
import com.rpm.pixelcat.logic.resource.MovableResource;
import com.rpm.pixelcat.logic.resource.Resource;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GameObjectUpdater {
    private static GameObjectUpdater instance;
    private static final Printer PRINTER = new Printer();

    private GameObjectUpdater() {
    }

    public static GameObjectUpdater getInstace() {
        if (instance == null) {
            instance = new GameObjectUpdater();
        }

        return instance;
    }

    public void update(GameObject gameObject, KernelState kernelState) throws GameException {
        updateForHIDEventGameLogicBindings(gameObject, kernelState);
        updateAnimation(gameObject, kernelState);
    }

    private void updateForHIDEventGameLogicBindings(GameObject gameObject, KernelState kernelState) throws GameException {
        // setup
        AnimationSequence animationSequence = gameObject.getCurrentAnimationSequence();
        Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings = gameObject.getGameObjectHIDEventLogicBehaviorBindings();
        HashSet<HIDEventEnum> triggeredHIDEvents = kernelState.getHIDEvents();

        // update object based on HID events
        for (GameObjectHIDEventLogicBehaviorBinding gameObjectHIDEventLogicBehaviorBinding : gameObjectHIDEventLogicBehaviorBindings) {
            // setup
            HIDEventEnum boundHIDEvent = gameObjectHIDEventLogicBehaviorBinding.getHidEventEnum();
            GameObjectLogicBehavior gameObjectLogicBehavior = gameObjectHIDEventLogicBehaviorBinding.getGameObjectLogicBehavior();

            // check that bound event was triggered
            if (!triggeredHIDEvents.contains(boundHIDEvent)) {
                continue;
            }

            // if so, handle its update action
            switch (gameObjectLogicBehavior.getBehaviorType()) {
                case MOVE_UP:
                case MOVE_DOWN:
                case MOVE_LEFT:
                case MOVE_RIGHT:
                    move(gameObject, kernelState, gameObjectLogicBehavior);
                    break;
                case ANIMATION_PLAY:
                    animationSequence.play();
                    break;
                case ANIMATION_STOP:
                    animationSequence.pause();
                    break;
                case ANIMATION_SEQUENCE_SWITCH:
                    gameObject.setCurrentOrientation(
                        ((GameObjectLogicParameterOrientation) gameObjectLogicBehavior.getGameObjectLogicParameterByClass(GameObjectLogicParameterOrientation.class)).getOrientation()
                    );
                    break;
            }
        }
    }

    private void updateAnimation(GameObject gameObject, KernelState kernelState) throws GameException {
        // setup
        AnimationSequence animationSequence = gameObject.getCurrentAnimationSequence();

        // step the animation forward as needed
        animationSequence.advanceSequenceByTime(kernelState.getClockTime());

        // record new current resource
        gameObject.setCurrentResource(animationSequence.getCurrentCel());
    }

    private void move(GameObject gameObject, KernelState kernelState, GameObjectLogicBehavior gameObjectLogicBehavior) {
        Double xVel = 0.0, yVel = 0.0;
        Double magnitude = 1.0;
        Rectangle screenBounds = kernelState.getBounds();

        // map event to velocity changes
        switch (gameObjectLogicBehavior.getBehaviorType()) {
            case MOVE_UP:
                yVel = -magnitude;
                break;
            case MOVE_DOWN:
                yVel = magnitude;
                break;
            case MOVE_RIGHT:
                xVel = magnitude;
                break;
            case MOVE_LEFT:
                xVel = -magnitude;
                break;
            default:
               // do nothing
                break;
        }

            // debug
            if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.DEBUG_ENABLED)) {
                PRINTER.printDebug("B:" + gameObjectLogicBehavior);
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
