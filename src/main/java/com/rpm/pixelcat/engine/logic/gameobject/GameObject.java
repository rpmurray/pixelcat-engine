package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.resource.Resource;

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

    public Set<GameObjectHIDEventLogicBehaviorBinding> getGameObjectHIDEventLogicBehaviorBindings();

    public Boolean hasAnimation();

    public OrientationEnum getCurrentOrientation() throws GameException;

    public void setCurrentOrientation(OrientationEnum currentOrientation);

    public AnimationSequence getCurrentAnimationSequence() throws GameException;

    public CollisionHandlingTypeEnum getCollisionHandlingTypeEnum();

    public ScreenBoundsHandlingTypeEnum getScreenBoundsHandlingTypeEnum();
}
