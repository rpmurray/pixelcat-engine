package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.awt.*;

public interface ImageResource extends MeasurableResource, CollidableResource {
    Boolean isLoaded();

    void load() throws TransientGameException;

    SpriteResource getMainResource();
}
