package info.masterfrog.pixelcat.engine.logic.gameobject.element.physics.bounds;

import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.physics.PhysicsBinding;

public interface BoundsHandler extends Aspect, PhysicsBinding {
    BoundsHandlingTypeEnum getBoundsType();

    static BoundsHandler createScreenBoundsHandler(BoundsHandlingTypeEnum boundsType) {
        return new ScreenBoundsHandlerImpl(boundsType);
    }

    static BoundsHandler createCanvasBoundsHandler(BoundsHandlingTypeEnum boundsType) {
        return new CanvasBoundsHandlerImpl(boundsType);
    }
}
