package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface SpriteSheet {
    Boolean isLoaded();

    void load() throws TransientGameException;

    public Rectangle getSpriteCelBounds(Integer xIndex, Integer yIndex);

    public BufferedImage getTexture();
}
