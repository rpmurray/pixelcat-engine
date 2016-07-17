package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

public interface ImageResource extends MeasurableResource, CollidableResource {
    Boolean isLoaded();

    void load() throws TransientGameException;

    SpriteResource getMainResource();
}
