package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.logic.animation.AnimationSequence;
import com.rpm.pixelcat.logic.resource.Resource;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class GameObjectImpl implements GameObject {
    private Point position;
    private Integer layer;
    private Set<HIDEventEnum> boundHIDEvents;
    private Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences;
    OrientationEnum currentOrientation;
    private Resource currentResource;

    public GameObjectImpl(Integer x, Integer y,
                          Integer layer,
                          Set<HIDEventEnum> boundHIDEvents,
                          Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                          OrientationEnum currentOrientation,
                          Resource currentResource) {
        setPosition(x, y);
        this.layer = layer;
        this.boundHIDEvents = boundHIDEvents;
        this.orientationBoundAnimationSequences = orientationBoundAnimationSequences;
        setCurrentOrientation(currentOrientation);
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

    public Set<HIDEventEnum> getBoundHIDEvents() {
        return boundHIDEvents;
    }

    public OrientationEnum getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(OrientationEnum currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    public AnimationSequence getCurrentAnimationSequence() {
        return orientationBoundAnimationSequences.get(currentOrientation);
    }
}
