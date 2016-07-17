package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.common.file.FileLoader;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.*;
import java.awt.image.BufferedImage;

class SpriteSheetImpl implements SpriteSheet {
    String filename;
    Integer celWidth;
    Integer celHeight;
    Integer topGutter;
    Integer bottomGutter;
    Integer leftGutter;
    Integer rightGutter;
    BufferedImage texture;

    SpriteSheetImpl(String filename,
                    Integer celWidth,
                    Integer celHeight,
                    Integer leftGutter,
                    Integer rightGutter,
                    Integer topGutter,
                    Integer bottomGutter) {
        this.filename = filename;
        this.celWidth = celWidth;
        this.celHeight = celHeight;
        this.leftGutter = leftGutter;
        this.rightGutter = rightGutter;
        this.topGutter = topGutter;
        this.bottomGutter = bottomGutter;
    }

    public Boolean isLoaded() {
        return texture != null;
    }

    public void load() throws TransientGameException {
        if (texture == null) {
            texture = FileLoader.getInstance().loadImage(filename);
        }
    }

    public Rectangle getSpriteCelBounds(Integer xIndex, Integer yIndex) {
        Rectangle spriteCelLocation = new Rectangle(
            xIndex * celWidth + leftGutter,
            yIndex * celHeight + topGutter,
            celWidth - leftGutter - rightGutter,
            celHeight - topGutter - bottomGutter
        );

        return spriteCelLocation;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    @Override
    public String toString() {
        return "SpriteSheetImpl{" +
            "fileName='" + filename + '\'' +
            ", celWidth=" + celWidth +
            ", celHeight=" + celHeight +
            ", topGutter=" + topGutter +
            ", bottomGutter=" + bottomGutter +
            ", leftGutter=" + leftGutter +
            ", rightGutter=" + rightGutter +
            ", texture=" + texture +
            '}';
    }
}
