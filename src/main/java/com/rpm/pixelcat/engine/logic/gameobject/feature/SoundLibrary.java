package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.resource.SoundResource;

public interface SoundLibrary extends LibraryTemplate<SoundResource> {
    static SoundLibrary create() throws TransientGameException {
        return Feature.create(SoundLibraryImpl.class);
    }
}
