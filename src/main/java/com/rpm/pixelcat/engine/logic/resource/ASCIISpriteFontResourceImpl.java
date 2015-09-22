package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;

import java.awt.*;

class ASCIISpriteFontResourceImpl implements ASCIISpriteFontResource {
    private SpriteSheet spriteSheet;
    private Integer characterSpacing;
    // definitions are indexed by integer type casted value of the given character
    private Point[] characterCoordinatesDefinition;
    private Integer[] characterWidthDefinition;

    // covers ASCII + extended ASCII
    private static final Integer CHARACTER_RANGE = 256;

    public ASCIISpriteFontResourceImpl(SpriteSheet spriteSheet,
                                       Integer characterSpacing) {
        this.spriteSheet = spriteSheet;
        this.characterSpacing = characterSpacing;
        this.characterCoordinatesDefinition = new Point[CHARACTER_RANGE];
        this.characterWidthDefinition = new Integer[CHARACTER_RANGE];
    }

    public ASCIISpriteFontResource defineCharacter(Character c, Integer width, Integer xIndex, Integer yIndex) {
        defineCharacter(c, width, new Point(xIndex, yIndex));

        return this;
    }

    public ASCIISpriteFontResource defineCharacter(Character c, Integer width, Point coordinates) {
        characterWidthDefinition[index(c)] = width;
        characterCoordinatesDefinition[index(c)] = coordinates;

        return this;
    }

    public Boolean isLoaded() {
        return spriteSheet.isLoaded();
    }

    public void load() throws GameException {
        spriteSheet.load();
    }

    public Rectangle getCelBounds(Character c) throws GameException {
        // lookup character coordinates
        Point coordinates = getCharacterCoordinates(c);

        // get base sprite cel bounds
        Rectangle celBounds = spriteSheet.getSpriteCelBounds(coordinates.x, coordinates.y);

        // adjust cel bounds based on character width
        celBounds.width = getCharacterWidth(c);

        return celBounds;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public Integer getCharacterWidth(Character c) {
        return characterWidthDefinition[index(c)];
    }

    public Integer getCharacterSpacing() {
        return characterSpacing;
    }

    private Point getCharacterCoordinates(Character c) throws GameException {
        // validate lookup
        if (index(c) >= characterCoordinatesDefinition.length) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // look up coordinates
        Point coordinates = characterCoordinatesDefinition[index(c)];

        // validate coordinates
        if (coordinates == null) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return coordinates;
    }

    private Integer index(Character c) {
        return (int) c;
    }
}
