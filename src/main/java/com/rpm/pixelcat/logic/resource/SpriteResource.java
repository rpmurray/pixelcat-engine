package com.rpm.pixelcat.logic.resource;

import com.rpm.pixelcat.exception.GameException;

public interface SpriteResource extends MovableResource {
    public Boolean isLoaded();

    public void load() throws GameException;

    public SpriteSheet getSpriteSheet();
}
