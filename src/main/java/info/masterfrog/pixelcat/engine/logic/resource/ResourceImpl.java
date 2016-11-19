package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.AspectImpl;

abstract class ResourceImpl extends AspectImpl implements Resource, Aspect {
    ResourceImpl() {
        super(Resource.class.getSimpleName());
    }

    ResourceImpl(String base) {
        super(base);
    }
}
