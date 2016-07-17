package info.masterfrog.pixelcat.engine.logic.camera;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.common.RenderableContainer;

public interface Camera extends IdGenerator {
    Class<? extends RenderableContainer> getType();

    String getView();

    static Camera create(String view, Class<? extends RenderableContainer> type) throws TransientGameException {
        Camera camera = new CameraImpl(view, type);

        return camera;
    }
}
