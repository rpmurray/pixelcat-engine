package com.rpm.pixelcat.logic.resource.model;

import com.rpm.pixelcat.exception.GameException;

public interface SpriteResource extends MovableResource
{
    public Boolean isLoaded();

    public void load() throws GameException;

    public SpriteSheet getSpriteSheet();

    public double getX();

    public double getY();
}
