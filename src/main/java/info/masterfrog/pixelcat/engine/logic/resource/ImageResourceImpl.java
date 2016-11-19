package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ImageResourceImpl extends ResourceImpl implements ImageResource {
    private SpriteResource mainResource;
    private Map<String, CollisionMaskResource> collisionMasks;

    ImageResourceImpl(SpriteResource mainResource, Set<CollisionMaskResource> collisionMasks) {
        super(ImageResource.class.getSimpleName());
        this.mainResource = mainResource;
        this.collisionMasks = new HashMap<>();
        if (collisionMasks != null) {
            for (CollisionMaskResource collisionMask : collisionMasks) {
                String collisionMaskId = collisionMask.getId();
                this.collisionMasks.put(collisionMaskId, collisionMask);
            }
        }
    }

    public Boolean isLoaded() {
        Boolean isLoaded = mainResource.isLoaded();
        for (SpriteResource collisionMask : collisionMasks.values()) {
            isLoaded &= collisionMask.isLoaded();
        }

        return isLoaded;
    }

    public void load() throws TransientGameException {
        mainResource.load();
        for (SpriteResource collisionMask : collisionMasks.values()) {
            if (collisionMask != null) {
                collisionMask.load();
            }
        }
    }

    public Rectangle getCelSize() {
        return mainResource.getCelSize();
    }

    public SpriteResource getMainResource() {
        return mainResource;
    }

    public Boolean hasCollisionMasks() {
        return !collisionMasks.isEmpty();
    }

    public Boolean hasCollisionMask(String id) {
        return collisionMasks.containsKey(id);
    }

    public CollisionMaskResource getCollisionMask(String id) {
        return collisionMasks.get(id);
    }

    @Override
    public String toString() {
        return "ImageResourceImpl{" +
            "mainResource=" + mainResource +
            ", collisionMasks=" + collisionMasks +
            '}';
    }
}
