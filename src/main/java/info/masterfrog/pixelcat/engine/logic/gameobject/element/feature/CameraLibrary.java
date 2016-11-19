package info.masterfrog.pixelcat.engine.logic.gameobject.element.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.camera.Camera;
import info.masterfrog.pixelcat.engine.logic.gameobject.util.library.LibraryTemplate;

public interface CameraLibrary extends LibraryTemplate<Camera> {
    static CameraLibrary create() throws TransientGameException {
        return Feature.create(CameraLibraryImpl.class);
    }
}
