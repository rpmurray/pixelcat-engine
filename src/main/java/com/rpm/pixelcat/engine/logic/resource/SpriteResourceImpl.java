package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;

import java.awt.*;
import java.awt.image.BufferedImage;

class SpriteResourceImpl extends IdGeneratorImpl implements SpriteResource {
    SpriteSheet spriteSheet;
    Integer xIndex;
    Integer yIndex;

    SpriteResourceImpl(SpriteSheet spriteSheet, Integer xIndex, Integer yIndex) {
        super(SpriteResource.class.toString());
        this.spriteSheet = spriteSheet;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public Boolean isLoaded() {
        return spriteSheet.isLoaded();
    }

    public void load() throws GameException {
        spriteSheet.load();
    }

    public Rectangle getCelBounds() {
        Rectangle celBounds = spriteSheet.getSpriteCelBounds(xIndex, yIndex);

        return celBounds;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    @Override
    public String toString() {
        return "ImageObject{" +
            "spriteSheet=" + spriteSheet +
            ", xIndex=" + xIndex +
            ", yIndex=" + yIndex +
            '}';
    }
}
