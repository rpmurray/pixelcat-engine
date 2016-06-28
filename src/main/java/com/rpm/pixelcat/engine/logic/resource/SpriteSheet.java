package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface SpriteSheet {
    Boolean isLoaded();

    void load() throws TransientGameException;

    public Rectangle getSpriteCelBounds(Integer xIndex, Integer yIndex);

    public BufferedImage getTexture();
}
