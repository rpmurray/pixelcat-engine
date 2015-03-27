package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameException;

import java.awt.*;

public interface ImageResource extends MeasurableResource, CollidableResource {
    public Boolean isLoaded();

    public void load() throws GameException;

    public Rectangle getCelBounds();

    public SpriteResource getMainResource();

    public Boolean hasCollisionMaskResource();

    public SpriteResource getCollisionMaskResource();
}
