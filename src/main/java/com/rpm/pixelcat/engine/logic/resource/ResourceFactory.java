package com.rpm.pixelcat.engine.logic.resource;

import java.awt.*;

public class ResourceFactory {
    private static ResourceFactory instance;

    private ResourceFactory() {
        // do nothing
    }

    public static ResourceFactory getInstance() {
        if (instance == null) {
            instance = new ResourceFactory();
        }

        return instance;
    }

    public SpriteResource createImageResource(Integer xIndex, Integer yIndex, SpriteSheet spriteSheet) {
        SpriteResource resource = new SpriteResourceImpl(spriteSheet, xIndex, yIndex);

        return resource;
    }

    public TextResource createTextResource(String text, Font font) {
        TextResource resource = new TextResourceImpl(text, font);

        return resource;
    }

    public SpriteSheet createSpriteSheet(String filename,
                                         Integer celWidth,
                                         Integer celHeight) {
        SpriteSheet spriteSheet = new SpriteSheetImpl(
            filename,
            celWidth,
            celHeight,
            0,
            0,
            0,
            0
        );

        return spriteSheet;
    }

    public SpriteSheet createSpriteSheet(String filename,
                                         Integer celWidth,
                                         Integer celHeight,
                                         Integer gutter) {
        SpriteSheet spriteSheet = new SpriteSheetImpl(
            filename,
            celWidth,
            celHeight,
            gutter,
            gutter,
            gutter,
            gutter
        );

        return spriteSheet;
    }

    public SpriteSheet createSpriteSheet(String filename,
                                         Integer celWidth,
                                         Integer celHeight,
                                         Integer xGutter,
                                         Integer yGutter) {
        SpriteSheet spriteSheet = new SpriteSheetImpl(
            filename,
            celWidth,
            celHeight,
            xGutter,
            xGutter,
            yGutter,
            yGutter
        );

        return spriteSheet;
    }

    public SpriteSheet createSpriteSheet(String filename,
                                         Integer celWidth,
                                         Integer celHeight,
                                         Integer leftGutter,
                                         Integer rightGutter,
                                         Integer topGutter,
                                         Integer bottomGutter) {
        SpriteSheet spriteSheet = new SpriteSheetImpl(
            filename,
            celWidth,
            celHeight,
            leftGutter,
            rightGutter,
            topGutter,
            bottomGutter
        );

        return spriteSheet;
    }
}
