package com.rpm.pixelcat.engine.logic.physics.collision;

import com.rpm.pixelcat.engine.logic.resource.CollidableResource;

public interface CollisionUtil {
    public Boolean collides(CollidableResource resource1, CollidableResource resource2);
}
