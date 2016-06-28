package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.resource.Resource;

public interface ResourceLibrary extends LibraryTemplate<Resource> {
    static ResourceLibrary create() throws TransientGameException {
        return Feature.create(ResourceLibraryImpl.class);
    }
}

