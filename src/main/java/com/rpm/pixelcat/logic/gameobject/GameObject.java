package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.logic.animation.AnimationSequence;
import com.rpm.pixelcat.logic.resource.Resource;

import java.awt.*;
import java.util.Set;

public interface GameObject {
    public void setCurrentResource(Resource resource);

    public Resource getCurrentResource();

    public void setPosition(Integer x, Integer y);

    public void setPosition(Point position);

    public Point getPosition();

    public void setLayer(Integer layer);

    public Integer getLayer();

    public Set<HIDEventEnum> getBoundHIDEvents();

    public OrientationEnum getCurrentOrientation();

    public void setCurrentOrientation(OrientationEnum currentOrientation);

    public AnimationSequence getCurrentAnimationSequence();
}
