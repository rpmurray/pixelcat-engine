package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.resource.Resource;

public interface ResourceLibrary extends LibraryContainerTemplate<Resource> {
    static ResourceLibrary create() throws GameException {
        return Feature.create(ResourceLibrary.class);
    }
}

