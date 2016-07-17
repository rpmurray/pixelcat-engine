package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.common.IdGeneratorImpl;

abstract class ResourceImpl extends IdGeneratorImpl implements Resource, IdGenerator {
    ResourceImpl() {
        super(Resource.class.getSimpleName());
    }

    ResourceImpl(String base) {
        super(base);
    }
}
