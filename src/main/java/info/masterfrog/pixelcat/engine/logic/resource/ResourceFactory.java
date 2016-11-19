package info.masterfrog.pixelcat.engine.logic.resource;

import java.awt.Font;
import java.util.Set;

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

    public SpriteResource createSpriteResource(Integer xIndex, Integer yIndex, SpriteSheet spriteSheet) {
        SpriteResource resource = new SpriteResourceImpl(spriteSheet, xIndex, yIndex, 1.0f);

        return resource;
    }

    public SpriteResource createSpriteResource(Integer xIndex, Integer yIndex, SpriteSheet spriteSheet, Float alphaMask) {
        SpriteResource resource = new SpriteResourceImpl(spriteSheet, xIndex, yIndex, alphaMask);

        return resource;
    }

    public ImageResource createImageResource(SpriteResource mainResource) {
        ImageResource resource = new ImageResourceImpl(mainResource, null);

        return resource;
    }

    public ImageResource createImageResource(SpriteResource mainResource, Set<CollisionMaskResource> collisionMasks) {
        ImageResource resource = new ImageResourceImpl(mainResource, collisionMasks);

        return resource;
    }

    public SoundResource createSoundResource(String fileName, float volume) {
        SoundResource resource = new SoundResourceImpl(fileName, volume);

        return resource;
    }

    public SoundResource createSoundResource(String fileName) {
        SoundResource resource = new SoundResourceImpl(fileName);

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
