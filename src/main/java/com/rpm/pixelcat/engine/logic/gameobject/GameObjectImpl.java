package com.rpm.pixelcat.engine.logic.gameobject;

import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.resource.Resource;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;

import java.awt.*;
import java.util.Map;
import java.util.Set;

class GameObjectImpl implements GameObject {
    private GameObjectProperties properties;
    private Point position;
    private Integer layer;
    private Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings;
    private Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences;
    private OrientationEnum currentOrientation;
    private Resource currentResource;
    private CollisionHandlingTypeEnum collisionHandlingTypeEnum;
    private ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum;

    GameObjectImpl(Integer x, Integer y,
                   Integer layer,
                   GameObjectProperties properties,
                   Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                   Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                   OrientationEnum currentOrientation,
                   Resource currentResource,
                   Boolean animationEnabled,
                   CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                   ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException {
        init(
            x, y,
            layer,
            properties,
            gameObjectHIDEventLogicBehaviorBindings,
            orientationBoundAnimationSequences,
            currentOrientation,
            currentResource,
            animationEnabled,
            collisionHandlingTypeEnum,
            screenBoundsHandlingTypeEnum
        );
    }

    GameObjectImpl(Integer x, Integer y,
                   Integer layer,
                   GameObjectProperties properties,
                   Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                   Resource currentResource,
                   CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                   ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException {
        init(
            x, y,
            layer,
            properties,
            gameObjectHIDEventLogicBehaviorBindings,
            null,
            null,
            currentResource,
            false,
            collisionHandlingTypeEnum,
            screenBoundsHandlingTypeEnum
        );
    }

    private void init(Integer x, Integer y,
                      Integer layer,
                      GameObjectProperties properties,
                      Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                      Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                      OrientationEnum currentOrientation,
                      Resource currentResource,
                      Boolean animationEnabled,
                      CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                      ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException {
        // position
        setPosition(x, y);

        // layer
        this.layer = layer;

        // properties
        this.properties = properties;

        // hid event logic behavior bindings
        this.gameObjectHIDEventLogicBehaviorBindings = gameObjectHIDEventLogicBehaviorBindings;

        // animation sequences + orientation
        if (currentOrientation != null) {
            this.orientationBoundAnimationSequences = orientationBoundAnimationSequences;
            setCurrentOrientation(currentOrientation);
        } else {
            this.orientationBoundAnimationSequences = ImmutableMap.<OrientationEnum, AnimationSequence>of();
            setCurrentOrientation(null);
        }

        // resource
        this.currentResource = currentResource;

        // animation
        if (currentOrientation != null) {
            if (animationEnabled) {
                getCurrentAnimationSequence().play();
            } else {
                getCurrentAnimationSequence().pause();
            }
        }

        // collisions + screen bounds
        this.collisionHandlingTypeEnum = collisionHandlingTypeEnum;
        this.screenBoundsHandlingTypeEnum = screenBoundsHandlingTypeEnum;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setPosition(Integer x, Integer y) {
        setPosition(new Point(x, y));
    }

    public Point getPosition() {
        return position;
    }

    public void setCurrentResource(Resource currentResource) {
        this.currentResource = currentResource;
    }

    public Resource getCurrentResource() {
        return currentResource;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getLayer() {
        return layer;
    }

    public GameObjectProperties getProperties() {
        return properties;
    }

    public Set<GameObjectHIDEventLogicBehaviorBinding> getGameObjectHIDEventLogicBehaviorBindings() {
        return gameObjectHIDEventLogicBehaviorBindings;
    }

    public Boolean hasAnimation() {
        return currentOrientation != null && orientationBoundAnimationSequences.size() > 0;
    }

    public OrientationEnum getCurrentOrientation() throws GameException {
        if (!hasAnimation()) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return currentOrientation;
    }

    public void setCurrentOrientation(OrientationEnum currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    public AnimationSequence getCurrentAnimationSequence() throws GameException {
        if (!hasAnimation()) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return orientationBoundAnimationSequences.get(currentOrientation);
    }

    public CollisionHandlingTypeEnum getCollisionHandlingTypeEnum() {
        return collisionHandlingTypeEnum;
    }

    public ScreenBoundsHandlingTypeEnum getScreenBoundsHandlingTypeEnum() {
        return screenBoundsHandlingTypeEnum;
    }

    @Override
    public String toString() {
        return "GameObjectImpl{" +
            "position=" + position +
            ", layer=" + layer +
            ", gameObjectHIDEventLogicBehaviorBindings=" + gameObjectHIDEventLogicBehaviorBindings +
            ", orientationBoundAnimationSequences=" + orientationBoundAnimationSequences +
            ", currentOrientation=" + currentOrientation +
            ", currentResource=" + currentResource +
            ", collisionHandlingTypeEnum=" + collisionHandlingTypeEnum +
            ", screenBoundsHandlingTypeEnum=" + screenBoundsHandlingTypeEnum +
            '}';
    }
}
