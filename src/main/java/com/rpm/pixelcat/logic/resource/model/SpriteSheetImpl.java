package com.rpm.pixelcat.logic.resource.model;

import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.logic.resource.FileLoader;

import java.awt.image.BufferedImage;

public class SpriteSheetImpl implements SpriteSheet {
    String filename;
    BufferedImage texture;

    SpriteSheetImpl(String filename) {
        this.filename = filename;
    }

    Boolean isLoaded() {
        return texture != null;
    }

    void load() throws GameException {
        if (texture == null) {
            texture = FileLoader.getInstance().loadImage(filename);
        }
    }

    public BufferedImage getTexture() {
        return texture;
    }
}
