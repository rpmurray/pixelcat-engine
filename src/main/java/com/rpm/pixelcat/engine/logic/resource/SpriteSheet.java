package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameException;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface SpriteSheet {
    Boolean isLoaded();

    void load() throws GameException;

    public Rectangle getSpriteCelBounds(Integer xIndex, Integer yIndex);

    public BufferedImage getTexture();
}
