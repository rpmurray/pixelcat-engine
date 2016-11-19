package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.Rectangle;

class SpriteResourceImpl extends ResourceImpl implements SpriteResource {
    SpriteSheet spriteSheet;
    Integer xIndex;
    Integer yIndex;
    Float alphaMask;

    SpriteResourceImpl(SpriteSheet spriteSheet, Integer xIndex, Integer yIndex, Float alphaMask) {
        super(SpriteResource.class.getSimpleName());
        this.spriteSheet = spriteSheet;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.alphaMask = alphaMask;
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
    public Float getAlphaMask() {
        return alphaMask;
    }

    @Override
    public void setAlphaMask(Float alphaMask) {
        this.alphaMask = alphaMask;
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
