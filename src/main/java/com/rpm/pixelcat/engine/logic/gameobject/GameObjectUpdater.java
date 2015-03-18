package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.resource.MovableResource;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GameObjectUpdater {
    private static GameObjectUpdater instance;
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(GameObjectUpdater.class);

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
                    if (!gameObject.hasAnimation()) {
                        throw new GameException(GameErrorCode.LOGIC_ERROR);
                    }
                    gameObject.getCurrentAnimationSequence().play();
                    break;
                case ANIMATION_STOP:
                    if (!gameObject.hasAnimation()) {
                        throw new GameException(GameErrorCode.LOGIC_ERROR);
                    }
                    gameObject.getCurrentAnimationSequence().pause();
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
        // precondition check
        if (!gameObject.hasAnimation()) {
            return;
        }

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
            PRINTER.printTrace("B:" + gameObjectLogicBehavior);

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
            PRINTER.printTrace(" SB: " + screenBounds + " GOP:" + position);
    }
}
