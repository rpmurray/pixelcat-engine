package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.Point;
import java.awt.Rectangle;

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
