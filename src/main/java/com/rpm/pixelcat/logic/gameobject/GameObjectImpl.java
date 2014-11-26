package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.logic.resource.model.Resource;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

abstract class GameObjectImpl implements GameObject {
    private Point position;
    private Integer layer;
    private Set<HIDEventEnum> boundHIDEvents;
    private WeakReference<Resource> currentResourceReference;

    public GameObjectImpl() {
        setPosition(0, 0);
        currentResourceReference = null;
        layer = null;
        boundHIDEvents = new HashSet<>();
    }

    public GameObjectImpl(Integer x, Integer y) {
        this();
        setPosition(x, y);
    }

    public GameObjectImpl(Resource currentResource) {
        this();
        setCurrentResource(currentResource);
    }

    public GameObjectImpl(Integer x, Integer y, Resource currentResource) {
        this();
        setPosition(x, y);
        setCurrentResource(currentResource);
    }

    public GameObjectImpl(Integer x, Integer y, Integer layer, Set<HIDEventEnum> boundHIDEvents, WeakReference<Resource> currentResourceReference) {
        this();
        setPosition(x, y);
        this.layer = layer;
        this.boundHIDEvents = boundHIDEvents;
        this.currentResourceReference = currentResourceReference;
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

    public void setCurrentResource(Resource resource) {
        currentResourceReference = new WeakReference<Resource>(resource);
    }

    public Resource getCurrentResource() {
        Resource currentResource = null;
        if (currentResourceReference != null) {
            currentResource = currentResourceReference.get();
        }

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
}
