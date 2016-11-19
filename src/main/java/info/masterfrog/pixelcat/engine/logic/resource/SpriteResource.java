package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.Rectangle;

public interface SpriteResource extends MeasurableResource {
    Boolean isLoaded();

    void load() throws TransientGameException;

    Rectangle getCelBounds();

    SpriteSheet getSpriteSheet();

    Float getAlphaMask();

    void setAlphaMask(Float alphaMask);
}
