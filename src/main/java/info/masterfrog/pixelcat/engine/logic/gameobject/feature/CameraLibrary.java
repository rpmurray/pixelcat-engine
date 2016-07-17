package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.camera.Camera;

public interface CameraLibrary extends LibraryTemplate<Camera> {
    static CameraLibrary create() throws TransientGameException {
        return Feature.create(CameraLibraryImpl.class);
    }
}
