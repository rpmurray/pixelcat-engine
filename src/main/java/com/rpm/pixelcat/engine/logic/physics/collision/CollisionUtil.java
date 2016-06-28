package com.rpm.pixelcat.engine.logic.physics.collision;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;

import java.awt.*;

public interface CollisionUtil {
    Boolean collidesByPixel(GameObject gameObject1, String collisionMaskId1, GameObject gameObject2, String collisionMaskId2) throws TransientGameException;

    Boolean collidesByBoundary(GameObject gameObject1, GameObject gameObject2) throws TransientGameException;

    public Boolean doRawMasksCollideByPixel(boolean[][] mask1, Rectangle bounds1, boolean[][] mask2, Rectangle bounds2);

    static CollisionUtil create() {
        CollisionUtil instance = CollisionUtilImpl.getInstance();

        return instance;
    }
}
