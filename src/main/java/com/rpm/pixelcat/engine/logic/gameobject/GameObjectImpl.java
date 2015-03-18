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
    private Point position;
    private Integer layer;
    private Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings;
    private Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences;
    OrientationEnum currentOrientation;
    private Resource currentResource;

    public GameObjectImpl(Integer x, Integer y,
                          Integer layer,
                          Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                          Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                          OrientationEnum currentOrientation,
                          Resource currentResource,
                          Boolean animationEnabled) throws GameException {
        setPosition(x, y);
        this.layer = layer;
        this.gameObjectHIDEventLogicBehaviorBindings = gameObjectHIDEventLogicBehaviorBindings;
        this.orientationBoundAnimationSequences = orientationBoundAnimationSequences;
        setCurrentOrientation(currentOrientation);
        this.currentResource = currentResource;
        if (animationEnabled) {
            getCurrentAnimationSequence().play();
        } else {
            getCurrentAnimationSequence().pause();
        }
    }

    public GameObjectImpl(Integer x, Integer y,
                          Integer layer,
                          Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                          Resource currentResource) {
        setPosition(x, y);
        this.layer = layer;
        this.gameObjectHIDEventLogicBehaviorBindings = gameObjectHIDEventLogicBehaviorBindings;
        this.orientationBoundAnimationSequences = ImmutableMap.<OrientationEnum, AnimationSequence>of();
        setCurrentOrientation(null);
        this.currentResource = currentResource;
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
}
