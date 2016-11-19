package info.masterfrog.pixelcat.engine.logic.gameobject.element.physics.bounds;

import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.AspectImpl;

abstract class BoundsHandlerImpl extends AspectImpl implements BoundsHandler {
    private BoundsHandlingTypeEnum boundsType;

    BoundsHandlerImpl(BoundsHandlingTypeEnum boundsType) {
        super();
        this.boundsType = boundsType;
    }

    public BoundsHandlingTypeEnum getBoundsType() {
        return boundsType;
    }
}
