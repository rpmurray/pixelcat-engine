package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;

abstract class ResourceImpl extends IdGeneratorImpl implements Resource, IdGenerator {
    ResourceImpl() {
        super(Resource.class.getSimpleName());
    }

    ResourceImpl(String base) {
        super(base);
    }
}
