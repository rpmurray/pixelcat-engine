package com.rpm.pixelcat.logic.resource;

import com.rpm.pixelcat.exception.GameException;

import java.awt.*;

class SpriteResourceImpl implements SpriteResource {
    SpriteSheet spriteSheet;
    Integer xIndex;
    Integer yIndex;

    SpriteResourceImpl(SpriteSheet spriteSheet, Integer xIndex, Integer yIndex) {
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
