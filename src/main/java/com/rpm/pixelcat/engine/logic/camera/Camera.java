package com.rpm.pixelcat.engine.logic.camera;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;

public interface Camera extends IdGenerator {
    Class<? extends RenderableContainer> getType();

    String getView();

    static Camera create(String view, Class<? extends RenderableContainer> type) throws GameException {
        Camera camera = new CameraImpl(view, type);

        return camera;
    }
}
