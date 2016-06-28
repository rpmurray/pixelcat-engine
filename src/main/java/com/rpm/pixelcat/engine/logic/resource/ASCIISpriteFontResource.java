package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.awt.*;

public interface ASCIISpriteFontResource extends FontResource, LoadableResource {
    public ASCIISpriteFontResource defineCharacter(Character c, Integer width, Integer xIndex, Integer yIndex);

    public ASCIISpriteFontResource defineCharacter(Character c, Integer width, Point coordinates);

    public Boolean isLoaded();

    public void load() throws TransientGameException;

    public SpriteSheet getSpriteSheet();

    public Rectangle getCelBounds(Character c) throws TransientGameException;

    public Integer getCharacterWidth(Character c);

    public Integer getCharacterSpacing();
}
