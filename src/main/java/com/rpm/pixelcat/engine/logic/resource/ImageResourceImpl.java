package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameException;

import java.awt.*;

class ImageResourceImpl extends ResourceImpl implements ImageResource {
    private SpriteResource mainResource;
    private SpriteResource collisionMaskResource;

    ImageResourceImpl(SpriteResource mainResource, SpriteResource collisionMaskResource) {
        super(ImageResource.class.toString());
        this.mainResource = mainResource;
        this.collisionMaskResource = collisionMaskResource;
    }

    public Boolean isLoaded() {
        return mainResource.isLoaded() && (!hasCollisionMaskResource() || collisionMaskResource.isLoaded());
    }

    public void load() throws GameException {
        mainResource.load();
        if (collisionMaskResource != null) {
            collisionMaskResource.load();
        }
    }

    public Rectangle getCelBounds() {
        return mainResource.getCelBounds();
    }

    public SpriteResource getMainResource() {
        return mainResource;
    }

    public Boolean hasCollisionMaskResource() {
        return collisionMaskResource != null;
    }

    public SpriteResource getCollisionMaskResource() {
        return collisionMaskResource;
    }

    @Override
    public String toString() {
        return "ImageResourceImpl{" +
            "mainResource=" + mainResource +
            ", collisionMaskResource=" + collisionMaskResource +
            '}';
    }
}
