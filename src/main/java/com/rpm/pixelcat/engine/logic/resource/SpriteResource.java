package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.awt.*;

public interface SpriteResource extends MeasurableResource {
    Boolean isLoaded();

    void load() throws TransientGameException;

    Rectangle getCelBounds();

    SpriteSheet getSpriteSheet();
}
