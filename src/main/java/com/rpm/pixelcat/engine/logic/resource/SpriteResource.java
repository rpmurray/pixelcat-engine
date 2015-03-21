package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameException;

public interface SpriteResource extends MeasurableResource {
    public Boolean isLoaded();

    public void load() throws GameException;

    public SpriteSheet getSpriteSheet();
}
