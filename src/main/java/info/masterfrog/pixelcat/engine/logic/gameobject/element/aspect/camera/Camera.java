package info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.camera;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.RenderableContainer;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;

public interface Camera extends Aspect {
    Class<? extends RenderableContainer> getType();

    String getView();

    static Camera create(String view, Class<? extends RenderableContainer> type) throws TransientGameException {
        Camera camera = new CameraImpl(view, type);

        return camera;
    }
}
