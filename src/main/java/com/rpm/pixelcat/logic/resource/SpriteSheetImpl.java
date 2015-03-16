package com.rpm.pixelcat.logic.resource;

import com.rpm.pixelcat.exception.GameException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheetImpl implements SpriteSheet {
    String filename;
    Integer celWidth;
    Integer celHeight;
    BufferedImage texture;

    SpriteSheetImpl(String filename, Integer celWidth, Integer celHeight) {
        this.filename = filename;
        this.celWidth = celWidth;
        this.celHeight = celHeight;
    }

    public Boolean isLoaded() {
        return texture != null;
    }

    public void load() throws GameException {
        if (texture == null) {
            texture = FileLoader.getInstance().loadImage(filename);
        }
    }

    public Rectangle getSpriteCelBounds(Integer xIndex, Integer yIndex) {
        Rectangle spriteCelLocation = new Rectangle(xIndex * celWidth, yIndex * celHeight, celWidth, celHeight);

        return spriteCelLocation;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    @Override
    public String toString() {
        return "SpriteSheetImpl{" +
            "filename='" + filename + '\'' +
            ", celWidth=" + celWidth +
            ", celHeight=" + celHeight +
            ", texture=" + texture +
            '}';
    }
}
