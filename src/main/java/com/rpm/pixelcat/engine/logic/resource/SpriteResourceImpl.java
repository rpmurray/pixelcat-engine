package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.awt.*;

class SpriteResourceImpl extends ResourceImpl implements SpriteResource {
    SpriteSheet spriteSheet;
    Integer xIndex;
    Integer yIndex;

    SpriteResourceImpl(SpriteSheet spriteSheet, Integer xIndex, Integer yIndex) {
        super(SpriteResource.class.getSimpleName());
        this.spriteSheet = spriteSheet;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public Boolean isLoaded() {
        return spriteSheet.isLoaded();
    }

    public void load() throws TransientGameException {
        spriteSheet.load();
    }

    public Rectangle getCelBounds() {
        Rectangle celBounds = spriteSheet.getSpriteCelBounds(xIndex, yIndex);

        return celBounds;
    }

    public Rectangle getCelSize() {
        Rectangle celBounds = getCelBounds();
        Rectangle celSize = new Rectangle(0, 0, celBounds.width, celBounds.height);

        return celSize;
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
