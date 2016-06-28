package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.camera.Camera;

public interface CameraLibrary extends LibraryTemplate<Camera> {
    static CameraLibrary create() throws TransientGameException {
        return Feature.create(CameraLibraryImpl.class);
    }
}
