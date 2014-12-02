package com.rpm.pixelcat.logic.resource.model;

import com.rpm.pixelcat.exception.GameException;

import java.awt.*;

class SpriteResourceImpl extends Rectangle implements SpriteResource {
    SpriteSheetImpl spriteSheetImpl;

    SpriteResourceImpl(String filename, Integer x, Integer y, Integer width, Integer height) {
        super(x, y, width, height);
        spriteSheetImpl = new SpriteSheetImpl(filename);
    }

    public Boolean isLoaded() {
        return spriteSheetImpl.isLoaded();
    }

    public void load() throws GameException {
        spriteSheetImpl.load();
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheetImpl;
    }

    @Override
    public String toString() {
        return "ImageObject{" +
            "spriteSheet=" + spriteSheetImpl +
            ", x=" + x +
            ", y=" + y +
            ", width=" + width +
            ", height=" + height +
            '}';
    }
}
