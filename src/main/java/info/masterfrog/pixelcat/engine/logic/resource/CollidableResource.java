package info.masterfrog.pixelcat.engine.logic.resource;

import java.awt.*;

public interface CollidableResource extends Resource {
    public Rectangle getCelSize();

    public Boolean hasCollisionMasks();

    public Boolean hasCollisionMask(String id);

    public CollisionMaskResource getCollisionMask(String Id);
}
