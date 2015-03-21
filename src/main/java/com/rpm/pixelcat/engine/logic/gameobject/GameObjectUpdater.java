package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.resource.MeasurableResource;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

class GameObjectUpdater {
    private static GameObjectUpdater instance;
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(GameObjectUpdater.class);

    private GameObjectUpdater() {
    }

    static GameObjectUpdater getInstace() {
        if (instance == null) {
            instance = new GameObjectUpdater();
        }

        return instance;
    }

    void update(GameObject gameObject, KernelState kernelState) throws GameException {
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
                        ((GameObjectLogicBehaviorParameterOrientation) gameObjectLogicBehavior.getGameObjectLogicParameterByClass(GameObjectLogicBehaviorParameterOrientation.class)).getOrientation()
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
        animationSequence.advanceSequenceByTime();

        // record new current resource
        gameObject.setCurrentResource(animationSequence.getCurrentCel());
    }

    private void move(GameObject gameObject, KernelState kernelState, GameObjectLogicBehavior gameObjectLogicBehavior)
            throws GameException {
        // setup
        GameObjectLogicBehaviorParameterMagnitude magnitudeParameter;
        Double xVel = 0.0, yVel = 0.0;

        // derive move magnitude
        Double magnitude;
        try {
            magnitudeParameter = (GameObjectLogicBehaviorParameterMagnitude) gameObjectLogicBehavior.getGameObjectLogicParameterByClass(
                GameObjectLogicBehaviorParameterMagnitude.class
            );
            magnitude = magnitudeParameter.getMagnitude();
        } catch (GameException e) {
            PRINTER.printWarning("Logic behavior parameter lookup failed during game object movement for {GameObjectLogicBehaviorParameterMagnitude}");
            magnitude = 1.0;
        }

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
        Point initialPosition = gameObject.getPosition();
        Point finalPosition = new Point(
            (int) (initialPosition.getX() + xVel),
            (int) (initialPosition.getY() + yVel)
        );

        // screen bounds checking
        handleScreenBounds(gameObject, kernelState, finalPosition);

        // debug
        PRINTER.printTrace(
            " SB: " + kernelState.getBounds() +
                " GO_IP: " + initialPosition +
                " GO_FP: " + finalPosition +
                " SBH:" + gameObject.getScreenBoundsHandlingTypeEnum()
        );

        // set position
        gameObject.setPosition(finalPosition);
    }

    private void handleScreenBounds(GameObject gameObject, KernelState kernelState, Point finalPosition)
            throws GameException {
        // setup
        Resource resource = gameObject.getCurrentResource();
        Rectangle resourceBounds;
        Rectangle screenBounds = kernelState.getBounds();
        Rectangle collisionSpace;
        Point collisionOffset;
        Point positionOffset;

        // check that resource is measurable
        if (resource instanceof MeasurableResource) {
            resourceBounds = ((MeasurableResource) resource).getCelBounds();
        } else {
            return;
        }

        // determine checkable position of resource
        switch (gameObject.getScreenBoundsHandlingTypeEnum()) {
            case NONE:
                return;
            case OUTER_RESOURCE_EDGE:
                collisionSpace = new Rectangle(
                    finalPosition.x,
                    finalPosition.y,
                    resourceBounds.width,
                    resourceBounds.height
                );
                collisionOffset = new Point(
                    0,
                    0
                );
                positionOffset = new Point(
                    0,
                    0
                );
                break;
            case INNER_RESOURCE_EDGE:
                collisionSpace = new Rectangle(
                    finalPosition.x,
                    finalPosition.y,
                    resourceBounds.width,
                    resourceBounds.height
                );
                collisionOffset = new Point(
                    resourceBounds.width,
                    resourceBounds.height
                );
                positionOffset = new Point(
                    0,
                    0
                );
                break;
            case CENTER_RESOURCE:
                collisionSpace = new Rectangle(
                    finalPosition.x + (resourceBounds.width / 2),
                    finalPosition.y + (resourceBounds.height / 2),
                    0,
                    0
                );
                collisionOffset = new Point(
                    0,
                    0
                );
                positionOffset = new Point(
                    resourceBounds.width / 2,
                    resourceBounds.height / 2
                );
                break;
            default:
                throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // check bounds
        if (collisionSpace.getX() + collisionSpace.getWidth() - collisionOffset.getX() > screenBounds.getWidth()) {
            finalPosition.x = (int) (screenBounds.getWidth() - positionOffset.getX());
        }
        if (collisionSpace.getY() + collisionSpace.getHeight() - collisionOffset.getY() > screenBounds.getHeight()) {
            finalPosition.y = (int) (screenBounds.getHeight() - positionOffset.getY());
        }
        if (collisionSpace.getX() - collisionOffset.getX() < screenBounds.getX()) {
            finalPosition.x = (int) (screenBounds.getX() - positionOffset.getX());
        }
        if (finalPosition.getY() - collisionOffset.getY() < screenBounds.getY()) {
            finalPosition.y = (int) (screenBounds.getY() - positionOffset.getY());
        }
    }
}
