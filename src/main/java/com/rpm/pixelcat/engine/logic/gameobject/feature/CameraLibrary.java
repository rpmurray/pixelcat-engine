package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.camera.Camera;

public interface CameraLibrary extends LibraryContainerTemplate<Camera> {
    static CameraLibrary create() throws GameException {
        return Feature.create(CameraLibraryImpl.class);
    }
}
