package com.rpm.pixelcat.engine.logic.physics.collision;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Renderable;
import com.rpm.pixelcat.engine.logic.gameobject.feature.ResourceLibrary;
import com.rpm.pixelcat.engine.logic.resource.*;

import java.awt.*;

class CollisionUtilImpl implements CollisionUtil {
    private static CollisionUtil instance = null;

    // private constructor for singleton
    private CollisionUtilImpl() {
        // do nothing
    }

    static CollisionUtil getInstance() {
        if (instance == null) {
            instance = new CollisionUtilImpl();
        }

        return instance;
    }

    public Boolean collidesByPixel(GameObject gameObject1, String collisionMaskId1, GameObject gameObject2, String collisionMaskId2) throws TransientGameException {
        // general collides by boundary check first
        if (!collidesByBoundary(gameObject1, gameObject2)) {
            return false;
        }

        // look up renderable bounds
        Rectangle bounds1 = getResourceBounds(gameObject1);
        Rectangle bounds2 = getResourceBounds(gameObject2);

        // look up collision masks
        CollisionMaskResource collisionMaskResource1 = getCollisionMask(gameObject1, collisionMaskId1);
        CollisionMaskResource collisionMaskResource2 = getCollisionMask(gameObject2, collisionMaskId2);

        // generate raw collision masks
        boolean[][] collisionMaskRaw1 = SpriteUtil.create().convertBufferedImageToRaw2dMask(collisionMaskResource1.getSpriteSheet().getTexture());
        boolean[][] collisionMaskRaw2 = SpriteUtil.create().convertBufferedImageToRaw2dMask(collisionMaskResource2.getSpriteSheet().getTexture());

        return doRawMasksCollideByPixel(collisionMaskRaw1, bounds1, collisionMaskRaw2, bounds2);
    }

    public Boolean doRawMasksCollideByPixel(boolean[][] mask1, Rectangle bounds1, boolean[][] mask2, Rectangle bounds2) {
        // calculate pixel by pixel collision on two masks
        for (int i = 0; i < mask1.length; i++) {
            for (int j = 0; j < mask1[i].length; j++) {
                // setup
                Point position1 = new Point(bounds1.x + i, bounds1.y + j);  // position defined by bounds plus pixel index
                boolean pixel1 = mask1[i][j];

                if (bounds2.contains(position1)) {
                    // setup
                    Point index2 = new Point(position1.x - bounds2.x, position1.y - bounds2.y); // pixel index defined by position minus bounds
                    boolean pixel2 = mask2[index2.x][index2.y];

                    // if both pixels are set, we have a collision
                    if (pixel1 && pixel2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Boolean collidesByBoundary(GameObject gameObject1, GameObject gameObject2) throws TransientGameException {
        // look up renderable bounds
        Rectangle bounds1 = getResourceBounds(gameObject1);
        Rectangle bounds2 = getResourceBounds(gameObject2);

        return bounds1.intersects(bounds2);
    }

    private Rectangle getResourceBounds(GameObject gameObject) throws TransientGameException {
        // extract render coordinates
        Point position = gameObject.getFeature(Renderable.class).getPosition();

        // extract current resource from game object
        Resource resource = gameObject.getFeature(ResourceLibrary.class).getCurrent();

        // validate that it is a collidable resource and that collision physics can be performed on it
        if (!(resource instanceof CollidableResource)) {
            throw new TransientGameException(GameErrorCode.PHYSICS_COLLISION_LOGIC_ERROR);
        }

        // setup
        CollidableResource collidableResource = (CollidableResource) resource;

        // extract cel renderableBounds, only use width/height, position is not defined
        Rectangle celBounds = collidableResource.getCelSize();

        // generate renderable bounds
        Rectangle renderableBounds = new Rectangle(position.x, position.y, celBounds.width, celBounds.height);

        return renderableBounds;
    }

    private CollisionMaskResource getCollisionMask(GameObject gameObject, String collisionMaskId) throws TransientGameException {
        // extract current resource from game object
        Resource resource = gameObject.getFeature(ResourceLibrary.class).getCurrent();

        // validate that it is a collidable resource and that collision physics can be performed on it
        if (!(resource instanceof CollidableResource)) {
            throw new TransientGameException(GameErrorCode.PHYSICS_COLLISION_LOGIC_ERROR);
        }

        // setup
        CollidableResource collidableResource = (CollidableResource) resource;

        // extract collision mask
        CollisionMaskResource collisionMaskResource;
        if (collidableResource.hasCollisionMask(collisionMaskId)) {
            collisionMaskResource = collidableResource.getCollisionMask(collisionMaskId);
        } else {
            throw new TransientGameException(GameErrorCode.PHYSICS_COLLISION_LOGIC_ERROR);
        }

        return collisionMaskResource;
    }
}
